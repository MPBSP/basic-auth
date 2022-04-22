package com.mpb.basic.auth.backend.web.exception;

public class ExceptionWithMessage extends RuntimeException {
    public ExceptionWithMessage(String message) {
        super(message);
    }
}
