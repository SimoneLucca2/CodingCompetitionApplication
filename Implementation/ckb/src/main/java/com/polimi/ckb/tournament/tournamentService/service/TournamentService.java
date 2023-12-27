package com.polimi.ckb.tournament.tournamentService.service;

import com.polimi.ckb.tournament.tournamentService.dto.CreateTournamentMessage;
import com.polimi.ckb.tournament.tournamentService.entity.Tournament;

public interface TournamentService {
    Tournament createTournament(CreateTournamentMessage createTournamentMessage);
}
