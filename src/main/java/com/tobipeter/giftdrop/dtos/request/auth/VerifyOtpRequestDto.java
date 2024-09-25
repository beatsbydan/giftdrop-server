package com.tobipeter.giftdrop.dtos.request.auth;

import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class VerifyOtpRequestDto {
    @NotBlank(message = "OTP cannot be empty")
    private String otp;
}
