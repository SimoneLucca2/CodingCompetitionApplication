package com.polimi.ckb.battleService.exception;

public class TournamentNotActiveException extends RuntimeException {
    public TournamentNotActiveException() {
        super("Tournament in not in ACTIVE state");
    }
}
