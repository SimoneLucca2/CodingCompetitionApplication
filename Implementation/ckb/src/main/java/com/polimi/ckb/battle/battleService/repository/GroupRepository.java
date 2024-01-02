package com.polimi.ckb.battle.battleService.repository;

import com.polimi.ckb.battle.battleService.entity.Group;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface GroupRepository extends JpaRepository<Group, Long> {
}
