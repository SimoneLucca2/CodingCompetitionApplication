package com.polimi.ckb.user.repository;

import com.polimi.ckb.user.entity.Battle;
import com.polimi.ckb.user.entity.Educator;
import org.springframework.data.jpa.repository.JpaRepository;

public interface BattleRepository extends JpaRepository<Battle, Long> {
}
