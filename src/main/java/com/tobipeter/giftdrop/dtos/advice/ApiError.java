package com.tobipeter.giftdrop.dtos.advice;

import lombok.Data;
import org.springframework.http.HttpStatus;

import java.time.ZonedDateTime;
import java.util.Map;

@Data
public class ApiError {
    private String message;

    private HttpStatus httpStatus;

    private ZonedDateTime timeStamp;

    private Map<String, String> errors;

    public ApiError(String message, HttpStatus httpStatus, ZonedDateTime timeStamp) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.timeStamp = timeStamp;
    }
    public ApiError(String message, HttpStatus httpStatus, ZonedDateTime timeStamp, Map<String, String> errors) {
        this.message = message;
        this.httpStatus = httpStatus;
        this.timeStamp = timeStamp;
        this.errors = errors;
    }
}
