package com.tobipeter.giftdrop.dtos.response.user;

import lombok.Data;

@Data
public class UserResponse {
    private String code;

    private String wishingId;

    private String giftingId;

    private String firstName;

    private String userName;

    private String email;

    private String address;

    private String bio;

    private String role;

    private Long points;

    private boolean canGift;

    private boolean canWish;

    private String profileImg;

    private String phone;

}