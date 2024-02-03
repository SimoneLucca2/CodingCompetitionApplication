package com.polimi.ckb.tournament.service;

import com.polimi.ckb.tournament.dto.CreateTournamentDto;
import com.polimi.ckb.tournament.dto.StudentJoinTournamentDto;
import com.polimi.ckb.tournament.dto.StudentQuitTournamentDto;
import com.polimi.ckb.tournament.dto.UpdateStudentScoreInTournamentDto;
import com.polimi.ckb.tournament.entity.Tournament;

import javax.validation.Valid;
import java.util.List;

public interface TournamentService {
    Tournament createTournament(CreateTournamentDto msg);
    Tournament getTournament(Long id);
    List<Tournament> getAllTournaments();
    List<Tournament> getPreparationTournaments();
    List<Tournament> getActiveTournaments();
    void updateTournamentScore(UpdateStudentScoreInTournamentDto msg);
    Tournament joinTournament(@Valid StudentJoinTournamentDto msg);
    Tournament leaveTournament(@Valid StudentQuitTournamentDto msg);

}
