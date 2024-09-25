package com.tobipeter.giftdrop.exceptions;

public class ForbiddenException extends Exception {
    public ForbiddenException(String message) {
        super(message);
    }

    public ForbiddenException(String message, Throwable cause) {
        super(message, cause);
    }
}