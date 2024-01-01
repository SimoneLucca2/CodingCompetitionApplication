package com.polimi.ckb.tournament.tournamentService.repository;

import com.polimi.ckb.tournament.tournamentService.entity.Score;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ScoreRepository extends JpaRepository<Score, Long> {
}