package com.tobipeter.giftdrop.services.auth;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tobipeter.giftdrop.db.models.Window;
import com.tobipeter.giftdrop.db.models.auth.GiftDropUser;
import com.tobipeter.giftdrop.db.models.auth.Otp;
import com.tobipeter.giftdrop.db.services.auth.user.UserService;
import com.tobipeter.giftdrop.db.services.window.WindowService;
import com.tobipeter.giftdrop.dtos.request.auth.*;
import com.tobipeter.giftdrop.dtos.response.MessageResponse;
import com.tobipeter.giftdrop.dtos.response.auth.AuthResponse;
import com.tobipeter.giftdrop.enums.Role;
import com.tobipeter.giftdrop.exceptions.*;
import com.tobipeter.giftdrop.services.MailingMgtService;
import com.tobipeter.giftdrop.util.TokenTypeUtil;
import jakarta.servlet.http.Cookie;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import lombok.SneakyThrows;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Lazy;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseCookie;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.authentication.logout.LogoutHandler;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@Slf4j
@RequiredArgsConstructor
public class AuthMgtService implements LogoutHandler {
    private final JwtMgtService jwtMgtService;
    private final OtpMgtService otpMgtService;
    private final MailingMgtService mailingMgtService;
    private final UserService userService;
    private final WindowService windowService;
    private final PasswordEncoder passwordEncoder;
    @Lazy
    @Autowired
    private AuthenticationManager authManager;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Value("${giftdrop.security.refresh-token.expiration}")
    private long refreshTokenExpiration;
    @Value("${giftdrop.security.reset-token.expiration}")
    private long resetTokenExpiration;

    public MessageResponse createUser(CreateUser request) throws DuplicateEntryException {
        boolean isExistingUser = userService.checkExistence(request);
        if(isExistingUser){
            throw new DuplicateEntryException("This user already exists. Consider changing the EMAIL or USERNAME.");
        }

        userService.save(createDbModel(request));

        return new MessageResponse("User created successfully");
    }

    public AuthResponse logIn(LogInRequest request, HttpServletResponse httpResponse) throws NotFoundException, ForbiddenException {
        try{
            authManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            request.getUserName(),
                            request.getPassword()
                    )
            );
        }
        catch(AuthenticationException ex){
            log.warn(ex.getMessage());
            throw new ForbiddenException("Invalid credentials");
        }

        UserDetails currentUserAuthDetails = userService.loadUserByUsername(request.getUserName());
        GiftDropUser currentUser = userService.getByUsername(request.getUserName());
        Window activeWindow = windowService.getActiveWindow();

        String accessToken = jwtMgtService.generateAccessToken(currentUserAuthDetails);
        String refreshToken = jwtMgtService.generateRefreshToken(currentUserAuthDetails);

        setTokenCookie(httpResponse, refreshToken, TokenTypeUtil.REFRESH_TOKEN, refreshTokenExpiration);
        return toResponse(currentUser, accessToken, activeWindow);
    }

    public MessageResponse verifyEmail(VerifyEmailRequest request, HttpServletResponse httpResponse) throws NotFoundException, MailingException {
        GiftDropUser user = userService.getByEmail(request.getEmail());

        String otpValue = mailingMgtService.sendOtpToUser(user);

        Otp otp = new Otp();
        otp.setOtp(otpValue);

        otpMgtService.save(otp);
        userService.save(user);

        user.setOtp(otp);


        String resetToken = jwtMgtService.generateResetToken(user);
        setTokenCookie(httpResponse, resetToken, TokenTypeUtil.RESET_TOKEN, resetTokenExpiration);

        return new MessageResponse("A One Time Password (OTP) was sent to your mail.");
    }

    public MessageResponse verifyOtp(VerifyOtpRequest request, HttpServletRequest httpRequest) throws RequestValidationException, NotFoundException {
        String token = getTokenFromCookie(httpRequest.getCookies(), TokenTypeUtil.RESET_TOKEN);
        String email = jwtMgtService.extractUsername(token);

        userService.getByEmail(email);

        if(!otpMgtService.isValid(request.getOtp())){
            throw new RequestValidationException("OTP has expired, please try again");
        }

        return new MessageResponse("OTP successfully validated.");
    }

    public MessageResponse resetPassword(ResetPasswordRequest request, HttpServletRequest httpRequest, HttpServletResponse httpResponse) throws NotFoundException {
        String token = getTokenFromCookie(httpRequest.getCookies(), TokenTypeUtil.RESET_TOKEN);
        String email = jwtMgtService.extractUsername(token);

        GiftDropUser user = userService.getByEmail(email);

        user.setPassword(passwordEncoder.encode(request.getNewPassword()));
        userService.save(user);

        clearCookies(httpRequest, httpResponse);

        return new MessageResponse("Password changed successfully!");
    }

    public AuthResponse refresh(
            HttpServletRequest request
    ) throws UnauthorizedException, NotFoundException {
        String refreshToken = getTokenFromCookie(request.getCookies(), TokenTypeUtil.REFRESH_TOKEN);

        String userName = jwtMgtService.extractUsername(refreshToken);
        Window activeWindow = windowService.getActiveWindow();

        String newAccessToken = null;
        GiftDropUser currentUser = new GiftDropUser();

        if(userName != null){
            try{
                currentUser = this.userService.getByUsername(userName);

                if(jwtMgtService.isTokenValid(refreshToken, currentUser)){
                    newAccessToken = jwtMgtService.generateAccessToken(currentUser);
                }
                else{
                    throw new UnauthorizedException("Session Expired, Please log back in.");
                }
            }
            catch(NotFoundException e){
                log.info("User does not exist.");
            }
        }
        else{
            throw new UnauthorizedException("Session Expired, Please log back in.");
        }

        return toResponse(currentUser, newAccessToken, activeWindow);
    }

    @SneakyThrows
    @Override
    public void logout(
            HttpServletRequest request,
            HttpServletResponse response,
            Authentication authentication
    ) {
        String authHeader = request.getHeader(HttpHeaders.AUTHORIZATION);

        if(authHeader == null || !authHeader.startsWith("Bearer ")){
            log.warn("Logout attempt with invalid Authorization header");
            throw new ForbiddenException("You're not authorized");
        }
        clearCookies(request, response);
        response.setStatus(HttpStatus.OK.value());
        response.getWriter().write(objectMapper.writeValueAsString(new MessageResponse("Logout was successful")));
    }

    private String getTokenFromCookie(Cookie[] cookies, String tokenType){
        return Arrays.stream(cookies)
                .filter(cookie -> cookie.getName().equals(tokenType))
                .findFirst()
                .map(Cookie::getValue).orElse(null);
    }

    private void setTokenCookie(HttpServletResponse response, String token, String tokenType, long expiration){
        ResponseCookie cookie = ResponseCookie.from(tokenType, token)
                .sameSite("Strict")
                .secure(true)
                .httpOnly(true)
                .maxAge(expiration)
                .build();
        response.addHeader(HttpHeaders.SET_COOKIE, cookie.toString());
    }

    private void clearCookies(HttpServletRequest request, HttpServletResponse response){
        for(Cookie cookie : request.getCookies()){
            cookie.setSecure(true);
            cookie.setHttpOnly(true);
            cookie.setValue(null);
            cookie.setMaxAge(0);
            response.addCookie(cookie);
        }
    }

    private GiftDropUser createDbModel(CreateUser request){
        GiftDropUser newUser = new GiftDropUser();

        newUser.setCode(newUser.generateCode());
        newUser.setWishingId(newUser.generateCode());
        newUser.setGiftingId(newUser.generateCode());
        newUser.setFirstName(request.getFirstName());
        newUser.setUsername(request.getUserName());
        newUser.setPassword(passwordEncoder.encode(request.getPassword()));
        newUser.setEmail(request.getEmail());
        newUser.setRole(Role.valueOf(request.getRole()));
        newUser.setHasWish(false);

        return newUser;
    }

    private AuthResponse toResponse(GiftDropUser currentUser, String token, Window window){
        AuthResponse response = new AuthResponse();

        response.setCode(currentUser.getCode());
        response.setWishingId(currentUser.getWishingId());
        response.setGiftingId(currentUser.getGiftingId());
        response.setFirstName(currentUser.getFirstName());
        response.setUserName(currentUser.getUsername());
        response.setEmail(currentUser.getEmail());
        response.setAddress(currentUser.getAddress());
        response.setBio(currentUser.getBio());
        response.setRole(currentUser.getRole().name());
        response.setProfileImg(currentUser.getProfileImage());
        response.setPhone(currentUser.getPhone());
        response.setToken(token);

        response.setCanGift(window.isGifting());
        response.setCanWish(window.isWishing());

        return response;
    }
}
