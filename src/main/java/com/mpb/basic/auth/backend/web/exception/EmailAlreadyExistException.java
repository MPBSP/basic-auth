package com.mpb.basic.auth.backend.web.exception;

public class EmailAlreadyExistException extends RuntimeException{
    public EmailAlreadyExistException() {
        super("Email is already exists");
    }
}
