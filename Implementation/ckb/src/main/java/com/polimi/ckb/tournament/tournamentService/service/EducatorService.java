package com.polimi.ckb.tournament.tournamentService.service;

import com.polimi.ckb.tournament.tournamentService.dto.AddEducatorDto;
import com.polimi.ckb.tournament.tournamentService.entity.Educator;

public interface EducatorService {
    Educator addEducatorToTournament(AddEducatorDto msg);
}
