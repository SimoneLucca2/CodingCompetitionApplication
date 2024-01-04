package com.polimi.ckb.battleService.exception;

public class BattleStateTooAdvancedException extends RuntimeException{

    public BattleStateTooAdvancedException(){
        super("Battle state is too advanced to perform this action");
    }
}
