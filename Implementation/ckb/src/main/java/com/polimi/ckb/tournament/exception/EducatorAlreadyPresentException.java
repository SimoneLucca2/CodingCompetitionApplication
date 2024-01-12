package com.polimi.ckb.tournament.exception;

public class EducatorAlreadyPresentException extends RuntimeException {
    public EducatorAlreadyPresentException() {
        super("Educator already present in the tournament");
    }
}