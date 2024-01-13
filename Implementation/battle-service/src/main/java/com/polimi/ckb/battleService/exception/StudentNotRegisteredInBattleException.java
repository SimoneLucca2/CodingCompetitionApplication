package com.polimi.ckb.battleService.exception;

public class StudentNotRegisteredInBattleException extends RuntimeException{
    public StudentNotRegisteredInBattleException(){
        super("Student is not registered in the battle");
    }
}
