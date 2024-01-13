package com.polimi.ckb.battleService.exception;

public class BattleAlreadyExistException extends RuntimeException {
    public BattleAlreadyExistException() {
        super("Battle name already exist within the given tournament");
    }
}
