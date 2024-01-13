package com.polimi.ckb.battleService.exception;

public class BattleDoesNotExistException extends RuntimeException{
    public BattleDoesNotExistException(){
        super("Battle does not exist");
    }
}
