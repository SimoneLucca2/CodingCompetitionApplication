package com.polimi.ckb.tournament.repository;

import com.polimi.ckb.tournament.entity.Score;
import com.polimi.ckb.tournament.entity.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ScoreRepository extends JpaRepository<Score, Long> {
    List<Score> findByTournament(Tournament tournament);
}