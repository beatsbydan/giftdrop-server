package com.tobipeter.giftdrop.dtos.advice;

import lombok.Data;

@Data
public class ApiResponse {
    private boolean success;

    private Object result;

    public ApiResponse(boolean success, Object result) {
        this.success = success;
        this.result = result;
    }
    public ApiResponse(boolean success) {
        this.success = success;
    }
}
