package com.tobipeter.giftdrop.exceptions;

public class DuplicateEntryException extends Exception{
    public DuplicateEntryException(String message) {
        super(message);
    }

    public DuplicateEntryException(String message, Throwable cause) {
        super(message, cause);
    }
}
