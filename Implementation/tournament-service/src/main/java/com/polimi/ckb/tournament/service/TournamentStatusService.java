package com.polimi.ckb.tournament.service;

import com.polimi.ckb.tournament.config.TournamentStatus;
import com.polimi.ckb.tournament.dto.ChangeTournamentStatusDto;
import com.polimi.ckb.tournament.entity.Tournament;

public interface TournamentStatusService {
    Tournament updateTournamentStatus(ChangeTournamentStatusDto msg);
    Tournament updateTournamentStatus(Long tournamentId, TournamentStatus status);
}
