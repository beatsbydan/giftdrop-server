package com.tobipeter.giftdrop.dtos.response.wish;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.util.List;

@Data
@AllArgsConstructor
public class ShareWishResponse {
    private String userName;

    private String profileImg;

    private String bio;

    private List<WishResponse> wishes;
}
