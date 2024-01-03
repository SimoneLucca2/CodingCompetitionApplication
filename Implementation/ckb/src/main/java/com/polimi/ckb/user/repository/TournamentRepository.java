package com.polimi.ckb.user.repository;

import com.polimi.ckb.user.entity.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TournamentRepository extends JpaRepository<Tournament, Long> {
}
