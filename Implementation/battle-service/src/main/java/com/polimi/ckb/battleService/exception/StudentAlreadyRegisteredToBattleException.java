package com.polimi.ckb.battleService.exception;

public class StudentAlreadyRegisteredToBattleException extends RuntimeException{
    public StudentAlreadyRegisteredToBattleException() {
        super("Student already registered to the battle");
    }
}
