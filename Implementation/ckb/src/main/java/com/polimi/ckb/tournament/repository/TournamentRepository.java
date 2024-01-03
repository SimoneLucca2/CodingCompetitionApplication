package com.polimi.ckb.tournament.repository;

import com.polimi.ckb.tournament.entity.Tournament;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long> {
    Optional<Tournament> findByName(String name);
    @NotNull Optional<Tournament> findById(@NotNull Long tournamentId);

}