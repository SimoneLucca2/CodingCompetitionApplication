package com.polimi.ckb.tournament.repository;

import com.polimi.ckb.tournament.entity.Tournament;
import org.jetbrains.annotations.NotNull;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.awt.print.Pageable;
import java.util.List;
import java.util.Optional;

@Repository
public interface TournamentRepository extends JpaRepository<Tournament, Long> {
    Optional<Tournament> findByName(String name);
    @NotNull Optional<Tournament> findById(@NotNull Long tournamentId);

    @Query("SELECT t FROM Tournament t WHERE " +
            "t.status = com.polimi.ckb.tournament.config.TournamentStatus.PREPARATION " +
            "ORDER BY t.registrationDeadline DESC")
    List<Tournament> getInPreparationTournaments();

    @Query("SELECT t FROM Tournament t WHERE " +
            "t.status = com.polimi.ckb.tournament.config.TournamentStatus.ACTIVE " +
            "ORDER BY t.registrationDeadline DESC")
    List<Tournament> getActiveTournaments();

}