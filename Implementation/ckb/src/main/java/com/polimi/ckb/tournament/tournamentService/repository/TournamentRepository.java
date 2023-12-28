package com.polimi.ckb.tournament.tournamentService.repository;

import com.polimi.ckb.tournament.tournamentService.entity.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long> {
}