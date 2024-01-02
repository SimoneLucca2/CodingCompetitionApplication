package com.polimi.ckb.battle.battleService.exception;

public class BattleAlreadyExistException extends RuntimeException {
    public BattleAlreadyExistException() {
        super("Battle already exist within the given tournament");
    }
}
