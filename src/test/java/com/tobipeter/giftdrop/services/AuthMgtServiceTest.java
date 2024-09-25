package com.tobipeter.giftdrop.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tobipeter.giftdrop.BaseIntegrationTest;
import com.tobipeter.giftdrop.dtos.request.auth.CreateUserDto;
import com.tobipeter.giftdrop.dtos.request.auth.LogInRequestDto;
import com.tobipeter.giftdrop.dtos.response.user.UserResponseDto;
import com.tobipeter.giftdrop.services.auth.AuthMgtService;
import com.tobipeter.giftdrop.services.auth.JwtMgtService;
import org.junit.jupiter.api.BeforeEach;
import org.springframework.beans.factory.annotation.Autowired;

public class AuthMgtServiceTest extends BaseIntegrationTest {
    @Autowired
    private AuthMgtService authMgtService;
    @Autowired
    private JwtMgtService jwtMgtService;

    private final ObjectMapper objectMapper = new ObjectMapper();
    private CreateUserDto createUserDto;
    private UserResponseDto userResponseDto;
    private LogInRequestDto logInRequestDto;

    @BeforeEach
    public void init(){

    }
}
