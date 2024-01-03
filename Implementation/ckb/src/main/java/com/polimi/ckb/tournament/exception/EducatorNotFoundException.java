package com.polimi.ckb.tournament.exception;

public class EducatorNotFoundException extends RuntimeException{
    public EducatorNotFoundException(Long id) {
        super("Educator with id " + id + " not found");
    }
}
