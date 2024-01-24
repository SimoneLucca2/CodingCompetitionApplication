package com.polimi.ckb.battleService.exception;

public class ErrorWhileCreatingRepositoryException extends RuntimeException{
    public ErrorWhileCreatingRepositoryException(String name){
        super("Error while creating repository with name " + name + "");
    }
}
