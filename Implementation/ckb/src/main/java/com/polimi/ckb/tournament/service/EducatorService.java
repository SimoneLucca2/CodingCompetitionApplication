package com.polimi.ckb.tournament.service;

import com.polimi.ckb.tournament.dto.AddEducatorDto;
import com.polimi.ckb.tournament.dto.NewUserDto;
import com.polimi.ckb.tournament.entity.Educator;

public interface EducatorService extends UserService{
    Educator addEducatorToTournament(AddEducatorDto msg);
    void addNewUser(NewUserDto msg);
}
