package com.polimi.ckb.user.service;

import com.polimi.ckb.user.dto.StudentJoinTournamentDto;
import com.polimi.ckb.user.entity.Tournament;

public interface TournamentService {
    Tournament saveTournament(Tournament msg);
    Tournament studentJoinTournament(StudentJoinTournamentDto msg);
}
