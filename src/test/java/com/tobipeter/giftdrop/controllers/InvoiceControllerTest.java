package com.tobipeter.giftdrop.controllers;

import com.tobipeter.giftdrop.BaseIntegrationTest;
import com.tobipeter.giftdrop.services.InvoiceMgtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class InvoiceControllerTest extends BaseIntegrationTest {
    @MockBean
    private InvoiceMgtService mgtService;

    @Override
    @BeforeEach
    public void setUp(){
        super.setUp();
    }

    @Test
    void should_get_invoices() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/invoices/123")
                .contentType(MediaType.APPLICATION_JSON)
                .param("page", "0")
                .param("size", "10"))
                .andDo(print())
                .andExpect(status().isOk());
    }
}