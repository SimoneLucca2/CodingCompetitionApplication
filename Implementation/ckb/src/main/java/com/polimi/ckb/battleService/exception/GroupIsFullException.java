package com.polimi.ckb.battleService.exception;

public class GroupIsFullException extends RuntimeException{
    public GroupIsFullException(){
        super("The group is full");
    }
}
