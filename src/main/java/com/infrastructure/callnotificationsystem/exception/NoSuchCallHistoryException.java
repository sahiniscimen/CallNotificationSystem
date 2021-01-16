package com.infrastructure.callnotificationsystem.exception;

public class NoSuchCallHistoryException extends RuntimeException{
    public NoSuchCallHistoryException(String message) {
        super(message);
    }
}
