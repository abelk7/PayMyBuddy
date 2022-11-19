package com.paymybuddy.paymybuddybackend.exception;

public class UserNotFoundException extends RuntimeException {

    public UserNotFoundException(String message) {
        super(String.format(message));
    }
}
