package com.tobipeter.giftdrop.dtos.response.wish;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
public class UserWishesResponseDto {
    private String wishingId;

    private String userName;

    private List<WishResponseDto> userWishes;
}
