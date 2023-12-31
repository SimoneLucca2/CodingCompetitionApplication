package com.polimi.ckb.tournament.tournamentService.service;

import com.polimi.ckb.tournament.tournamentService.dto.ChangeTournamentStatusDto;
import com.polimi.ckb.tournament.tournamentService.entity.Tournament;

public interface TournamentStatusService {
    Tournament updateTournamentStatus(ChangeTournamentStatusDto msg);
}
