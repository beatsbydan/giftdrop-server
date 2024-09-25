package com.tobipeter.giftdrop.dtos.request.auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UpdateUser extends CreateUser {
    private String address;

    private String bio;

    private String phone;
}
