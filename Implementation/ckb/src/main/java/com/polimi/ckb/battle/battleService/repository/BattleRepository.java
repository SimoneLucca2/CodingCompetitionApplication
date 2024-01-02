package com.polimi.ckb.battle.battleService.repository;

import com.polimi.ckb.battle.battleService.entity.Battle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BattleRepository extends JpaRepository<Battle, Long> {
    Optional<Battle> findByName(String name);
    List<Battle> findByTournamentID(Long tournamentID);

    Integer deleteBattleByName(String name);
}
