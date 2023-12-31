package com.polimi.ckb.tournament.tournamentService.exception;

public class EducatorAlreadyPresentException extends RuntimeException {
    public EducatorAlreadyPresentException() {
        super("Educator already present in the tournament");
    }
}