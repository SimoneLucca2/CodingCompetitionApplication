package com.polimi.ckb.tournament.tournamentService.service;

import com.polimi.ckb.tournament.tournamentService.dto.ChangeTournamentStatusMessage;
import com.polimi.ckb.tournament.tournamentService.entity.Tournament;

public interface TournamentStatusService {
    Tournament updateTournamentStatus(ChangeTournamentStatusMessage msg);
}
