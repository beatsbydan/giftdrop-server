package com.tobipeter.giftdrop.dtos.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class LogInRequest {
    @NotBlank(message = "Username cannot be empty.")
    private String userName;

    @NotBlank(message = "password cannot be empty")
    private String password;
}
