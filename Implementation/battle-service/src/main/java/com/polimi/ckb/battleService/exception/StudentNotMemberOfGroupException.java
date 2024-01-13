package com.polimi.ckb.battleService.exception;

public class StudentNotMemberOfGroupException extends RuntimeException{
    public StudentNotMemberOfGroupException() {
        super("The student is not a member of the group");
    }
}
