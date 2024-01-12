package com.polimi.ckb.tournament.service;

import com.polimi.ckb.tournament.dto.NewUserDto;

public interface StudentService extends UserService{
    /**
     * adds a new student to the tournament database,
     * the student can also not be in a tournament yet
     */
    void addNewUser(NewUserDto msg);
}
