package com.polimi.ckb.battleService.exception;

public class CannotEvaluateGroupSolutionException extends RuntimeException{
    public CannotEvaluateGroupSolutionException() {
        super("Cannot evaluate the solution of the group");
    }
}
