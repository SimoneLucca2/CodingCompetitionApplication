package com.polimi.ckb.tournament.tournamentService.exception;

public class EducatorNotFoundException extends RuntimeException{
    public EducatorNotFoundException(Long id) {
        super("Educator with id " + id + " not found");
    }
}
