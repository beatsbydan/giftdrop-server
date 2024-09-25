package com.tobipeter.giftdrop.dtos.request.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUserDto extends CreateUserDto{
    private String address;

    private String bio;

    private String phone;
}
