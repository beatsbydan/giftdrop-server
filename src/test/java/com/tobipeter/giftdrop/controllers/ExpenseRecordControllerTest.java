package com.tobipeter.giftdrop.controllers;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.tobipeter.giftdrop.BaseIntegrationTest;
import com.tobipeter.giftdrop.dtos.request.expenseRecord.ExpenseRecordRequest;
import com.tobipeter.giftdrop.exceptions.NotFoundException;
import com.tobipeter.giftdrop.services.ExpenseRecordMgtService;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.doThrow;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

class ExpenseRecordControllerTest extends BaseIntegrationTest {
    @MockBean
    private ExpenseRecordMgtService mgtService;
    private final ObjectMapper objectMapper = new ObjectMapper();

    @Override
    @BeforeEach
    public void setUp(){
        super.setUp();
    }
    @Test
    void should_create_expense_record_SUCCESS() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/expense-records")
                        .content(objectMapper.writeValueAsString(toExpenseRecordRequest()))
                .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isCreated());
    }

    @Test
    void should_create_expense_record_FAILURE() throws Exception {
        doThrow(new NotFoundException("Not Found"))
                .when(mgtService).createExpenseRecord(any(ExpenseRecordRequest.class));
        mockMvc.perform(MockMvcRequestBuilders.post("/api/v1/expense-records")
                        .content(objectMapper.writeValueAsString(toExpenseRecordRequest()))
                        .contentType(MediaType.APPLICATION_JSON))
                .andDo(print())
                .andExpect(status().isNotFound());
    }

    private ExpenseRecordRequest toExpenseRecordRequest(){
        ExpenseRecordRequest request = new ExpenseRecordRequest();

        request.setDeliveryFee("test");
        request.setProductsFee("test");
        request.setServiceFee("test");
        request.setTotalFee("test");
        request.setUserCode("testCode");
        request.setWindowCode("testCode");

        return request;
    }
}