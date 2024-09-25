package com.tobipeter.giftdrop.exceptions;

public class MailingException extends Exception{
    public MailingException() {
        super();
    }

    public MailingException(String message) {
        super(message);
    }

    public MailingException(String message, Throwable cause) {
        super(message, cause);
    }
}
