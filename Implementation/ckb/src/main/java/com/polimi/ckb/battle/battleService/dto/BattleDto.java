package com.polimi.ckb.battle.battleService.dto;

import com.polimi.ckb.battle.battleService.entity.Battle;
import com.polimi.ckb.battle.battleService.entity.Educator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link Battle}
 */
@Value
@Builder
@Data
@AllArgsConstructor
public class BattleDto implements Serializable {
    String name;
    String description;
    Educator creatorId;
    String registrationDeadline;
    String submissionDeadline;
    Long tournamentID;
    Integer maxGroupSize;
    Integer minGroupSize;
}