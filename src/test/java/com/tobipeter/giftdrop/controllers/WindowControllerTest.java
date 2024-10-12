package com.tobipeter.giftdrop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tobipeter.giftdrop.BaseIntegrationTest;
import com.tobipeter.giftdrop.dtos.request.window.CreateWindow;
import com.tobipeter.giftdrop.exceptions.DuplicateEntryException;
import com.tobipeter.giftdrop.exceptions.NotFoundException;
import com.tobipeter.giftdrop.services.WindowMgtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

public class WindowControllerTest extends BaseIntegrationTest {
    @MockBean
    private WindowMgtService windowMgtService;

    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @BeforeEach
    public void setUp(){
        super.setUp();
    }

    @Test
    public void should_create_window_SUCCESS() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/windows")
                .content(objectMapper.writeValueAsString(toCreateWindowDto()))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    public void should_create_window_FAILURE() throws Exception {
        doThrow(new DuplicateEntryException("Window exists"))
                .when(windowMgtService).createWindow(toCreateWindowDto());
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/windows")
                .content(objectMapper.writeValueAsString(toCreateWindowDto()))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isConflict());
    }

    @Test
    public void should_get_past_expenses() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/windows/past-expenses")
                .param("page", "0")
                .param("size", "10")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    public void should_get_next_window_SUCCESS() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/windows/next")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    public void should_get_next_window_FAILURE() throws Exception{
        doThrow(new NotFoundException("Window not found"))
                .when(windowMgtService).getNextWindow();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/windows/next")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void should_get_active_window_SUCCESS() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/windows/active"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void should_get_active_window_FAILURE() throws Exception {
        doThrow(new NotFoundException("No active window present"))
                .when(windowMgtService).getActiveWindow();
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/windows/active"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    @Test
    public void should_activate_window_SUCCESS() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/windows/activate/123")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }
    @Test
    public void should_activate_window_FAILURE() throws Exception{
        doThrow(new NotFoundException("Window not found"))
                .when(windowMgtService).activateWindow("123");
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/windows/activate/123")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }
    @Test
    public void should_deactivate_window_SUCCESS() throws Exception{
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/windows/deactivate")
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void should_update_window_status_SUCCESS() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/windows/toggle/123")
                        .param("isWishing", "false")
                        .param("isGifting", "false"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void should_update_window_status_FAILURE() throws Exception {
        doThrow(new NotFoundException("No Window found"))
                .when(windowMgtService).updateWindowStatus("123", false, false);
        mockMvc.perform(MockMvcRequestBuilders.put("/api/v1/windows/toggle/123")
                        .param("isWishing", "false")
                        .param("isGifting", "false"))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    private CreateWindow toCreateWindowDto () {
        CreateWindow request = new CreateWindow();

        request.setStartDate(null);
        request.setEndDate(null);

        return request;
    }
}
