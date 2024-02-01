package com.polimi.ckb.battleService.exception;

public class ErrorWhileExecutingScannerException extends RuntimeException{
    public ErrorWhileExecutingScannerException(){
        super("Error while executing sonar-scanner");
    }
}
