package com.polimi.ckb.battleService.repository;

import com.polimi.ckb.battleService.entity.Battle;
import com.polimi.ckb.battleService.entity.StudentGroup;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface GroupRepository extends JpaRepository<StudentGroup, Long> {
    List<StudentGroup> findByBattle(Battle battle);

    @Query("SELECT g FROM StudentGroup g WHERE " +
            "g.battle = :battle " +
            "ORDER BY g.score DESC")
    List<StudentGroup> findScoreByBattle(Battle battle);

    StudentGroup findByClonedRepositoryLink(String link);
}
