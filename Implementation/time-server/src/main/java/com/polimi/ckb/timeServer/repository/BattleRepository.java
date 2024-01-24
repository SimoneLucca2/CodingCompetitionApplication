package com.polimi.ckb.timeServer.repository;

import com.polimi.ckb.timeServer.entity.Battle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface BattleRepository extends JpaRepository<Battle, Long> {
    @Query("SELECT t FROM Tournament t WHERE t.registrationDeadline > ?1 AND t.status = 'PRE_BATTLE'")
    List<Battle> findAllWithRegistrationDeadlinePassedAndStatusPreBattle(String deadline);

    @Query("SELECT t FROM Tournament t WHERE t.registrationDeadline > ?1 AND t.status = 'BATTLE'")
    List<Battle> findAllWithSubmissionDeadlinePassedAndStatusBattle(String deadline);
}