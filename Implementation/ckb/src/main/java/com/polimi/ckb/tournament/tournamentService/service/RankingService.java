package com.polimi.ckb.tournament.tournamentService.service;

import com.polimi.ckb.tournament.tournamentService.dto.RankingEntryDto;

import java.util.List;

public interface RankingService {
    List<RankingEntryDto> getTournamentRanking(Long tournamentId, Integer firstIndex, Integer lastIndex);
}
