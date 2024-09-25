package com.tobipeter.giftdrop.dtos.response.window;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class WindowResponse {
    private boolean active;

    private String code;

    private LocalDate startDate;

    private LocalDate endDate;

    private boolean isWishing;

    private boolean isGifting;

    private Long netWishLists;

    private Long activeGifters;

    private Long completedGifts;

    private Integer percentGiftingCompleted;

    private String giftersWishersRatio;

    private Long totalExpenses;

    private Long totalProductsFee;

    private Long totalServiceFee;

    private boolean isNextWindow;

    private RecentWindowResponse recentWindow;

    private List<RecentWindowResponse> pastTwoWindows;

    private NextWindowResponse nextWindow;
}
