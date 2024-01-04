package com.polimi.ckb.battleService.repository;

import com.polimi.ckb.battleService.entity.Battle;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface BattleRepository extends JpaRepository<Battle, Long> {
    List<Battle> findByTournamentId(Long tournamentID);

    void deleteBattleByName(String name);

    Optional<Battle> findById(Long id);
}
