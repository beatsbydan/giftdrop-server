package com.tobipeter.giftdrop.dtos.response.auth;

import com.tobipeter.giftdrop.dtos.response.user.UserResponse;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AuthResponse extends UserResponse {
    private String token;
}
