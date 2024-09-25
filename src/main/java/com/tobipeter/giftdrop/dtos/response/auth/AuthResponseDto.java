package com.tobipeter.giftdrop.dtos.response.auth;

import com.tobipeter.giftdrop.dtos.response.user.UserResponseDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@EqualsAndHashCode(callSuper = false)
public class AuthResponseDto extends UserResponseDto {
    private String token;
}
