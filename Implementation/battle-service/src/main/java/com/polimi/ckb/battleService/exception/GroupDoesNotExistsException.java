package com.polimi.ckb.battleService.exception;

public class GroupDoesNotExistsException extends RuntimeException{
    public GroupDoesNotExistsException(){
        super("Group does not exist");
    }
}
