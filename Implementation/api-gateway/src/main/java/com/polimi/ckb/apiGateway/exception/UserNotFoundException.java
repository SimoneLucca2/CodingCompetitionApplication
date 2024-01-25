package com.polimi.ckb.apiGateway.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(String message) {
        super("The user with email " + message + " does not exist");
    }
}
