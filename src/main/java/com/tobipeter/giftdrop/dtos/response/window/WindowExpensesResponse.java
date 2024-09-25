package com.tobipeter.giftdrop.dtos.response.window;

import lombok.Data;

import java.time.LocalDate;

@Data
public class WindowExpensesResponse {
    private LocalDate date;

    private Long totalServiceFee;

    private Long totalProductsFee;

    private Long totalExpenses;
}
