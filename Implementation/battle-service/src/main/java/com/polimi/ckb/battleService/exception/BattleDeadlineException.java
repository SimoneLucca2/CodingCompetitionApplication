package com.polimi.ckb.battleService.exception;

public class BattleDeadlineException extends RuntimeException{
    public BattleDeadlineException() {
        super("Battle deadlines are not in the right chronological order");
    }
}
