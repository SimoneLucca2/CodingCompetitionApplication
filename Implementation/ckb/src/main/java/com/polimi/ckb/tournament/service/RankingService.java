package com.polimi.ckb.tournament.service;

import com.polimi.ckb.tournament.dto.RankingEntryDto;

import java.util.List;

public interface RankingService {
    List<RankingEntryDto> getTournamentRanking(Long tournamentId, Integer firstIndex, Integer lastIndex);
}
