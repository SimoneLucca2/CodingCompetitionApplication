package com.polimi.ckb.tournament.service;

import com.polimi.ckb.tournament.dto.AddEducatorDto;
import com.polimi.ckb.tournament.dto.NewUserDto;
import com.polimi.ckb.tournament.entity.Tournament;

public interface EducatorService extends UserService{
    Tournament addEducatorToTournament(AddEducatorDto msg);
    void addNewUser(NewUserDto msg);
}
