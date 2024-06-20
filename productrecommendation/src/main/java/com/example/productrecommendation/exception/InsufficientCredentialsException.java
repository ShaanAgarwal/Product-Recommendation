package com.example.productrecommendation.exception;

public class InsufficientCredentialsException extends RuntimeException{
    public InsufficientCredentialsException(String message) {
        super(message);
    }
}
