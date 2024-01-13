package com.polimi.ckb.tournament.service;

import com.polimi.ckb.tournament.config.TournamentStatus;
import com.polimi.ckb.tournament.dto.CreateTournamentDto;
import com.polimi.ckb.tournament.dto.StudentJoinTournamentDto;
import com.polimi.ckb.tournament.dto.StudentQuitTournamentDto;
import com.polimi.ckb.tournament.dto.UpdateStudentScoreInTournamentDto;
import com.polimi.ckb.tournament.entity.Tournament;

import javax.validation.Valid;

public interface TournamentService {
    Tournament saveTournament(CreateTournamentDto msg);
    Tournament getTournament(Long id);
    void updateTournamentScore(UpdateStudentScoreInTournamentDto msg);
    Tournament joinTournament(@Valid StudentJoinTournamentDto msg);
    Tournament leaveTournament(@Valid StudentQuitTournamentDto msg);
    void updateTournamentStatus(Long tournamentId, TournamentStatus status);
}
