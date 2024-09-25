package com.tobipeter.giftdrop.dtos.request.wish;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UpdateWish {
    @NotBlank(message = "Code cannot be empty")
    private String code;

    @NotBlank(message = "Status cannot be empty")
    private String status;
}
