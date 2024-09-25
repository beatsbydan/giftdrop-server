package com.tobipeter.giftdrop.dtos.response.wish;

import lombok.Data;

@Data
public class WishResponse {
    private String wishingId;

    private String wishCode;

    private String wishName;

    private String userName;

    private String wishCategory;

    private Long wishPrice;

    private String wishLink;

    private String wishStatus;

}
