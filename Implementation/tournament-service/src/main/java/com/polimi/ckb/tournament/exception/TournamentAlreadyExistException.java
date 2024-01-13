package com.polimi.ckb.tournament.exception;

public class TournamentAlreadyExistException extends RuntimeException{
    public TournamentAlreadyExistException() {
        super("Tournament already exist");
    }
}
