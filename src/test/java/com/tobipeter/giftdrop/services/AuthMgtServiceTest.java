package com.tobipeter.giftdrop.services;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tobipeter.giftdrop.BaseIntegrationTest;
import com.tobipeter.giftdrop.dtos.request.auth.CreateUser;
import com.tobipeter.giftdrop.dtos.request.auth.LogInRequest;
import com.tobipeter.giftdrop.dtos.response.user.UserResponse;
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
    private CreateUser createUser;
    private UserResponse userResponse;
    private LogInRequest logInRequest;

    @BeforeEach
    public void init(){

    }
}
