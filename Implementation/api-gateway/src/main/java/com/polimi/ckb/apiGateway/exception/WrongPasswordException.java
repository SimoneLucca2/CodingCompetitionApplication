package com.polimi.ckb.apiGateway.exception;

public class WrongPasswordException extends RuntimeException{
    public WrongPasswordException(String message) {
        super("The password " + message + " is wrong");
    }
}
