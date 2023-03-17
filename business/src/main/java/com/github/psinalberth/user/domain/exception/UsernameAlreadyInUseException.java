package com.github.psinalberth.user.domain.exception;

public class UsernameAlreadyInUseException extends RuntimeException {

    public UsernameAlreadyInUseException(String message) {
        super(message);
    }
}
