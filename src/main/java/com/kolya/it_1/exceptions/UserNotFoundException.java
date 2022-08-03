package com.kolya.it_1.exceptions;

import lombok.Getter;
import org.springframework.security.core.AuthenticationException;

public class UserNotFoundException extends AuthenticationException {
    @Getter
    private String message;

    public UserNotFoundException() {
        super("User not found");
    }

    public UserNotFoundException(String message) {
        super(message);
        this.message = message;
    }
}
