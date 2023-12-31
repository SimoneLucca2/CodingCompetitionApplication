package com.polimi.ckb.tournament.tournamentService.exception;

public class TournamentNotFoundException extends RuntimeException{

    public TournamentNotFoundException() {
        super("Tournament not found");
    }
}
