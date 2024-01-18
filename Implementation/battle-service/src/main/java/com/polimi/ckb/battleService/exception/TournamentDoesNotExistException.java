package com.polimi.ckb.battleService.exception;

public class TournamentDoesNotExistException extends RuntimeException{
    public TournamentDoesNotExistException() {
        super("Tournament does not exist");
    }
}
