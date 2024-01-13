package com.polimi.ckb.battleService.exception;

public class StudentDoesNotExistException extends RuntimeException {
    public StudentDoesNotExistException() {
        super("Student does not exist");
    }
}
