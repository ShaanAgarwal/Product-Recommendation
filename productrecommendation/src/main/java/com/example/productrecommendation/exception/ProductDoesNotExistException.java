package com.example.productrecommendation.exception;

public class ProductDoesNotExistException extends RuntimeException{
    public ProductDoesNotExistException(String message) {
        super(message);
    }
}
