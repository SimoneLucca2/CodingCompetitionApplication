package com.polimi.ckb.battleService.exception;

public class ErrorWhileCreatingSonarQubeProjectException extends RuntimeException{
    public ErrorWhileCreatingSonarQubeProjectException(){
        super("Error while creating SonarQube project");
    }
}
