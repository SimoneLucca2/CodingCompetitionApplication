package com.polimi.ckb.user.service;

import com.polimi.ckb.user.dto.EducatorJoinTournamentDto;
import com.polimi.ckb.user.dto.StudentJoinTournamentDto;
import com.polimi.ckb.user.dto.StudentQuitsTournamentDto;
import com.polimi.ckb.user.entity.Tournament;

import java.util.Optional;

public interface TournamentService {
    Tournament saveTournament(Tournament msg);
    Tournament studentJoinTournament(StudentJoinTournamentDto msg);

    Tournament studentQuitsTournament(StudentQuitsTournamentDto msg);

    Tournament addEducatorToTournament(EducatorJoinTournamentDto msg);
}
