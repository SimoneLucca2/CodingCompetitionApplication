package com.polimi.ckb.battleService.exception;

public class BattleGroupConstraintException extends RuntimeException{
    public BattleGroupConstraintException() {
        super("Max group size is less than min group size");
    }
}
