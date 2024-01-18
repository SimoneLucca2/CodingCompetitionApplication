package com.polimi.ckb.battleService.exception;

public class EducatorNotAuthorizedException extends RuntimeException{
    public EducatorNotAuthorizedException() {
        super("Educator is not authorized to perform this action");
    }
}
