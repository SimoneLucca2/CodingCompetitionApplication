package com.polimi.ckb.timeServer.repository;

import com.polimi.ckb.timeServer.entity.Tournament;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TournamentRepository extends JpaRepository<Tournament, Long> {

   @Query("SELECT t FROM Tournament t WHERE t.registrationDeadline > ?1 AND t.status = 'PREPARATION'")
   List<Tournament> findAllWithDeadlineAfterAndStatusPreparation(String deadline);

}