package com.polimi.ckb.tournament.tournamentService.service;

import com.polimi.ckb.tournament.tournamentService.dto.CreateTournamentDto;
import com.polimi.ckb.tournament.tournamentService.dto.StudentJoinTournamentDto;
import com.polimi.ckb.tournament.tournamentService.dto.StudentQuitTournamentDto;
import com.polimi.ckb.tournament.tournamentService.dto.UpdateStudentScoreInTournamentDto;
import com.polimi.ckb.tournament.tournamentService.entity.Tournament;

import javax.validation.Valid;

public interface TournamentService {
    Tournament saveTournament(CreateTournamentDto msg);
    Tournament getTournament(Long id);
    void updateTournamentScore(UpdateStudentScoreInTournamentDto msg);
    Tournament joinTournament(@Valid StudentJoinTournamentDto msg);
    Tournament leaveTournament(@Valid StudentQuitTournamentDto msg);
}
