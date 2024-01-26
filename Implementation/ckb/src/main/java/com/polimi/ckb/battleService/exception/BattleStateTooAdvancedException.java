package com.polimi.ckb.battleService.exception;

import lombok.AllArgsConstructor;

public class BattleStateTooAdvancedException extends RuntimeException{

    public BattleStateTooAdvancedException(){
        super("Battle state is too advanced to perform this action");
    }
}
