package com.polimi.ckb.battleService.exception;

public class StudentAlreadyInAnotherGroupException extends RuntimeException{
    public StudentAlreadyInAnotherGroupException(){
        super("The student is already in another group");
    }
}
