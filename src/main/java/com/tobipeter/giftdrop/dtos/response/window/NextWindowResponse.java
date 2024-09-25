package com.tobipeter.giftdrop.dtos.response.window;

import lombok.Data;

import java.time.LocalDate;

@Data
public class NextWindowResponse {
    private String code;

    private LocalDate date;
}
