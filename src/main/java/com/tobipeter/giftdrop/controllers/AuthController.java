package com.tobipeter.giftdrop.controllers;

import com.tobipeter.giftdrop.dtos.request.auth.*;
import com.tobipeter.giftdrop.dtos.response.MessageResponse;
import com.tobipeter.giftdrop.dtos.response.auth.AuthResponse;
import com.tobipeter.giftdrop.exceptions.*;
import com.tobipeter.giftdrop.services.auth.AuthMgtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/auth")
@RequiredArgsConstructor
@Validated
public class AuthController {
    private final AuthMgtService mgtService;

    @PostMapping("/register")
    @ResponseStatus(HttpStatus.CREATED)
    public MessageResponse createUser(
            @RequestBody @Valid CreateUser request
    ) throws DuplicateEntryException {
        return mgtService.createUser(request);
    }

    @PostMapping("/login")
    public AuthResponse login(
            @Valid @RequestBody LogInRequest request,
            HttpServletResponse httpResponse
    ) throws NotFoundException, ForbiddenException {
        return mgtService.logIn(request, httpResponse);
    }

    @GetMapping("/verify-email")
    public MessageResponse verifyEmail(
            @Valid @RequestBody VerifyEmailRequest request,
            HttpServletResponse httpResponse
    ) throws NotFoundException, MailingException {
        return mgtService.verifyEmail(request, httpResponse);
    }

    @GetMapping("/verify-otp")
    public MessageResponse verifyOtp(
            @Valid @RequestBody VerifyOtpRequest request,
            HttpServletRequest httpRequest
    ) throws RequestValidationException, NotFoundException {
        return mgtService.verifyOtp(request, httpRequest);
    }

    @PutMapping("/reset-password")
    public MessageResponse resetPassword(
            @Valid @RequestBody ResetPasswordRequest request,
            HttpServletRequest httpRequest,
            HttpServletResponse httpResponse
    ) throws NotFoundException {
        return mgtService.resetPassword(request, httpRequest, httpResponse);
    }

    @GetMapping("/refresh")
    public AuthResponse refresh(
            HttpServletRequest request
    ) throws UnauthorizedException, NotFoundException {
        return mgtService.refresh(request);
    }
}