package com.polimi.ckb.battleService.exception;

public class BattleDeadlinesOverlapException extends RuntimeException{
    public BattleDeadlinesOverlapException(){
        super("The battle deadlines overlap with another battle");
    }
}
