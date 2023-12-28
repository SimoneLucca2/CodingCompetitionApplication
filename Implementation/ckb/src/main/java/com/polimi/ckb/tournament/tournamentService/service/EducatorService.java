package com.polimi.ckb.tournament.tournamentService.service;

import com.polimi.ckb.tournament.tournamentService.dto.AddEducatorMessage;
import com.polimi.ckb.tournament.tournamentService.entity.Educator;

public interface EducatorService {
    Educator addEducatorToTournament(AddEducatorMessage msg);
}
