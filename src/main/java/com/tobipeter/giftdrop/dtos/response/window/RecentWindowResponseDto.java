package com.tobipeter.giftdrop.dtos.response.window;

import lombok.Data;

import java.time.LocalDate;

@Data
public class RecentWindowResponseDto {
    private long totalAmountSpent;

    private long wishesGranted;

    private long totalWishers;

    private long totalGifters;

    private LocalDate startDate;
}
