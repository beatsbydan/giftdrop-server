package com.tobipeter.giftdrop.dtos.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class ResetPasswordRequest {
    @NotBlank(message = "Password cannot be empty")
    private String newPassword;
}
