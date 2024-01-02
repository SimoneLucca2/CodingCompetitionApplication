package com.polimi.ckb.tournament.tournamentService.repository;

import com.polimi.ckb.tournament.tournamentService.entity.Score;
import com.polimi.ckb.tournament.tournamentService.entity.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScoreRepository extends JpaRepository<Score, Long> {
    List<Score> findByTournament(Tournament tournament);
}