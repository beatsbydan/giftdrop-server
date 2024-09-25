package com.tobipeter.giftdrop.dtos.response.wish;

import lombok.Data;

import java.util.List;

@Data
public class UserWishesResponse {
    private String wishingId;

    private String userName;

    private List<WishResponse> userWishes;
}
