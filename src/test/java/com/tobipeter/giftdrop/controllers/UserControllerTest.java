package com.tobipeter.giftdrop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tobipeter.giftdrop.BaseIntegrationTest;
import com.tobipeter.giftdrop.dtos.request.auth.UpdateUser;
import com.tobipeter.giftdrop.exceptions.NotFoundException;
import com.tobipeter.giftdrop.services.UserMgtService;
import lombok.SneakyThrows;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class UserControllerTest extends BaseIntegrationTest {
    @MockBean
    private UserMgtService userMgtService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @BeforeEach
    public void setUp(){
        super.setUp();
    }

    @Test
    @SneakyThrows
    public void should_update_user_SUCCESS() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/users/123")
                .content(objectMapper.writeValueAsString(toUserRequestDto()))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    public void should_update_user_FAILURE() throws Exception{
        doThrow(new NotFoundException("User not found"))
                .when(userMgtService).updateUser(toUserRequestDto(), "123");
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/users/123")
                .content(objectMapper.writeValueAsString(toUserRequestDto()))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    @SneakyThrows
    public void should_get_a_user_by_code_SUCCESS() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/123"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @SneakyThrows
    public void should_get_paginated_users() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users")
                .param("page", "0")
                .param("size", "10"))
                .andDo((print()))
                .andExpect(status().isOk());
    }

    private UpdateUser toUserRequestDto (){
        UpdateUser dto = new UpdateUser();

        dto.setFirstName("Daniel");
        dto.setUserName("beats_by_dan");
        dto.setEmail("daniel@test.com");
        dto.setPassword("daniel@password");
        dto.setRole("GIFT_DROP_USER");
        dto.setAddress("Address");
        dto.setBio("New Bio");

        return dto;
    }
}
