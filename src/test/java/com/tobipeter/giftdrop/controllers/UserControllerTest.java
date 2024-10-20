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
    public void should_update_user_SUCCESS() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/users/123")
                .content(objectMapper.writeValueAsString(toUserRequestDto()))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
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
   public void should_get_ranked_users() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/users/rankings")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
   }

    private UpdateUser toUserRequestDto (){
        UpdateUser dto = new UpdateUser();

        dto.setProfileImg("profileImg");
        dto.setBio("New Bio");

        return dto;
    }
}
