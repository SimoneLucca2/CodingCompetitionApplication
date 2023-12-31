package com.polimi.ckb.battle.battleService.repository;

import com.polimi.ckb.battle.battleService.entity.BattleEntity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface BattleEntityRepository extends JpaRepository<BattleEntity, Long> {
    Optional<BattleEntity> findByName(String name);
}
