package com.tobipeter.giftdrop.dtos.request.expenseRecord;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ExpenseRecordRequest {
    @NotBlank(message = "Delivery fee cannot be blank.")
    private String deliveryFee;

    @NotBlank(message = "Service fee cannot be blank.")
    private String serviceFee;

    @NotBlank(message = "Products fee cannot be blank.")
    private String productsFee;

    @NotBlank(message = "Total fee cannot be blank.")
    private String totalFee;

    @NotBlank(message = "User code cannot be empty.")
    private String userCode;

    @NotBlank(message = "Window code cannot be empty.")
    private String windowCode;
}
