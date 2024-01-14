package com.polimi.ckb.battleService.exception;

public class BattleChangingStatusException extends RuntimeException {
    public BattleChangingStatusException(String message) {
        super(message);
    }
}
