package com.tobipeter.giftdrop.dtos.response.invoice;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class CreateInvoiceRequestDto {
    @NotBlank(message = "Amount cannot be empty")
    private String amountSpent;
}
