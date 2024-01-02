package com.polimi.ckb.battle.battleService.entity;

import com.polimi.ckb.battle.battleService.config.BattleStatus;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BattleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long BattleId;
    private String name;
    private String description;
    private Long creatorId;
    private String registrationDeadline;
    private String submissionDeadline;
    private String repoLink;
    private Long tournamentID;
    private BattleStatus status;
}
