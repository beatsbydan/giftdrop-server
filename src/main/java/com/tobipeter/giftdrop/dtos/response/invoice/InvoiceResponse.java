package com.tobipeter.giftdrop.dtos.response.invoice;

import lombok.Data;

import java.time.LocalDate;

@Data
public class InvoiceResponse {
    private String name;

    private Long amountSpent;

    private LocalDate date;
}
