package com.polimi.ckb.battleService.exception;

public class BattleRegistrationDeadlineExpiredException extends RuntimeException{
    public BattleRegistrationDeadlineExpiredException(){
        super("Battle registration deadline expired");
    }
}
