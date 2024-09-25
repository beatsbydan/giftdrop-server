package com.tobipeter.giftdrop.exceptions;

public class RequestValidationException extends Exception{
    public RequestValidationException(String message) {
        super(message);
    }

    public RequestValidationException(String message, Throwable cause) {
        super(message, cause);
    }
}
