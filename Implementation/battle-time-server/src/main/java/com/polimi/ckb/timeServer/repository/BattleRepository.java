package com.polimi.ckb.timeServer.repository;

import com.polimi.ckb.timeServer.entity.Battle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;

public interface BattleRepository extends JpaRepository<Battle, Long> {

    @Query("SELECT t FROM Battle t WHERE " +
            "t.registrationDeadline < :currentTime " +
            "AND t.status = com.polimi.ckb.timeServer.config.BattleStatus.PRE_BATTLE")
    List<Battle> findAllWithRegistrationDeadlinePassedAndStatusPreBattle(String currentTime);

    @Query("SELECT t FROM Battle t WHERE " +
            "t.submissionDeadline < :currentTime AND " +
            "t.status = com.polimi.ckb.timeServer.config.BattleStatus.BATTLE")
    List<Battle> findAllWithSubmissionDeadlinePassedAndStatusBattle(String currentTime);

}