package com.polimi.ckb.tournament.tournamentService.service;

import com.polimi.ckb.tournament.tournamentService.dto.AddEducatorDto;
import com.polimi.ckb.tournament.tournamentService.dto.NewUserDto;
import com.polimi.ckb.tournament.tournamentService.entity.Educator;

public interface EducatorService extends UserService{
    Educator addEducatorToTournament(AddEducatorDto msg);
    void addNewUser(NewUserDto msg);
}
