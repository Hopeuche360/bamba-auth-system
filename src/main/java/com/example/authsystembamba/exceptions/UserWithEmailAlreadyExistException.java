package com.example.authsystembamba.exceptions;

public class UserWithEmailAlreadyExistException extends RuntimeException {
    public UserWithEmailAlreadyExistException(String message) {
        super(message);
    }
}
