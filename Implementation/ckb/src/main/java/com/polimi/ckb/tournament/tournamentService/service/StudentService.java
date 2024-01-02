package com.polimi.ckb.tournament.tournamentService.service;

import com.polimi.ckb.tournament.tournamentService.dto.NewUserDto;

public interface StudentService extends UserService{
    /**
     * adds a new student to the tournament database,
     * the student can also not be in a tournament yet
     */
    void addNewUser(NewUserDto msg);
}
