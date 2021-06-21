package com.example.bank.errors_handler.errors;

public class ApiUserNotFoundException extends RuntimeException{
    public ApiUserNotFoundException(String message) {
        super(message);
    }

    public ApiUserNotFoundException(String message, Throwable cause) {
        super(message, cause);
    }
}
