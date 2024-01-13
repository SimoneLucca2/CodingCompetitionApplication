package com.polimi.ckb.tournament.exception;

public class TournamentNotFoundException extends RuntimeException{

    public TournamentNotFoundException() {
        super("Tournament not found");
    }
}
