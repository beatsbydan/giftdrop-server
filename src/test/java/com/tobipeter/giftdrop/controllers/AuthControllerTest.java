package com.tobipeter.giftdrop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tobipeter.giftdrop.BaseIntegrationTest;
import com.tobipeter.giftdrop.dtos.request.auth.*;
import com.tobipeter.giftdrop.exceptions.DuplicateEntryException;
import com.tobipeter.giftdrop.exceptions.NotFoundException;
import com.tobipeter.giftdrop.exceptions.RequestValidationException;
import com.tobipeter.giftdrop.exceptions.UnauthorizedException;
import com.tobipeter.giftdrop.services.auth.AuthMgtService;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class AuthControllerTest extends BaseIntegrationTest {
    @MockBean
    private AuthMgtService mgtService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @BeforeEach
    public void setUp(){
        super.setUp();
    }

    @Test
    public void should_register_a_user_SUCCESS() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/register")
                .content(objectMapper.writeValueAsString(toUserRequestDto()))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }
    @Test
    public void should_register_a_user_FAILURE() throws Exception {
        doThrow(new DuplicateEntryException("User already exists"))
                .when(mgtService).createUser(any(CreateUser.class));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/register")
                .content(objectMapper.writeValueAsString(toUserRequestDto()))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    public void should_log_a_user_in_SUCCESS() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/login")
                .content(objectMapper.writeValueAsString(toLogInRequestDto()))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void should_log_a_user_in_FAILURE() throws Exception {
        doThrow(new NotFoundException("User not found"))
                .when(mgtService).logIn(any(LogInRequest.class), any(HttpServletResponse.class));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/login")
                .content(objectMapper.writeValueAsString(toLogInRequestDto()))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void should_verify_email_SUCCESS() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/verify-email")
                .content(objectMapper.writeValueAsString(toVerifyEmailRequest()))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void should_verify_email_FAILURE() throws Exception {
        doThrow(new NotFoundException("User not found"))
                .when(mgtService).verifyEmail(any(VerifyEmailRequest.class), any(HttpServletResponse.class));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/verify-email")
                .content(objectMapper.writeValueAsString(toVerifyEmailRequest()))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
@Test
    public void should_verify_otp_SUCCESS() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/verify-otp")
                .content(objectMapper.writeValueAsString(toVerifyOtpRequest()))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void should_verify_otp_FAILURE() throws Exception {
        doThrow(new RequestValidationException("Invalid Otp"))
                .when(mgtService).verifyOtp(any(VerifyOtpRequest.class), any(HttpServletRequest.class));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/auth/verify-otp")
                .content(objectMapper.writeValueAsString(toVerifyOtpRequest()))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isBadRequest());
    }

    @Test
    public void should_reset_password_SUCCESS() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/auth/reset-password")
                .content(objectMapper.writeValueAsString(toResetPasswordRequest()))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void should_reset_password_FAILURE() throws Exception {
        doThrow(new NotFoundException("User not found"))
                .when(mgtService).resetPassword(any(ResetPasswordRequest.class), any(HttpServletRequest.class), any(HttpServletResponse.class));
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/auth/reset-password")
                .content(objectMapper.writeValueAsString(toResetPasswordRequest()))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void should_refresh_SUCCESS() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/auth/refresh"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void should_refresh_FAILURE() throws Exception {
        doThrow(new UnauthorizedException("You are unauthorized"))
                .when(mgtService).refresh(any(HttpServletRequest.class));
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/auth/refresh"))
                .andDo(print())
                .andExpect(status().isUnauthorized());
    }

    private CreateUser toUserRequestDto (){
        CreateUser dto = new CreateUser();

        dto.setFirstName("Daniel");
        dto.setUserName("beats_by_dan");
        dto.setEmail("daniel@test.com");
        dto.setPassword("daniel@password");
        dto.setRole("GIFT_DROP_USER");

        return dto;
    }

    private LogInRequest toLogInRequestDto(){
        LogInRequest dto = new LogInRequest();

        dto.setUserName("beats_by_dan");
        dto.setPassword("daniel@password");

        return dto;
    }

    private VerifyEmailRequest toVerifyEmailRequest(){
        VerifyEmailRequest request = new VerifyEmailRequest();

        request.setEmail("daniel@daniel.com");

        return request;
    }

    private VerifyOtpRequest toVerifyOtpRequest(){
        VerifyOtpRequest request = new VerifyOtpRequest();

        request.setOtp("0123");

        return request;
    }

    private ResetPasswordRequest toResetPasswordRequest(){
        ResetPasswordRequest request = new ResetPasswordRequest();

        request.setNewPassword("newPassword");

        return request;
    }
}
