package com.polimi.ckb.tournament.tournamentService.exception;

public class TournamentAlreadyExistException extends RuntimeException{
    public TournamentAlreadyExistException() {
        super("Tournament already exist");
    }
}
