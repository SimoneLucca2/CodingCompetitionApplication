package com.polimi.ckb.tournament.tournamentService.service;

import com.polimi.ckb.tournament.tournamentService.dto.CreateTournamentDto;
import com.polimi.ckb.tournament.tournamentService.dto.UpdateStudentScoreInTournamentDto;
import com.polimi.ckb.tournament.tournamentService.entity.Score;
import com.polimi.ckb.tournament.tournamentService.entity.Tournament;

public interface TournamentService {
    Tournament saveTournament(CreateTournamentDto msg);
    Tournament getTournament(Long id);
    Score updateTournamentScore(UpdateStudentScoreInTournamentDto id);
}
