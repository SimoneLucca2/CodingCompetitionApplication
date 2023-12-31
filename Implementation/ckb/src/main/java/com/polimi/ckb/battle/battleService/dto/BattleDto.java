package com.polimi.ckb.battle.battleService.dto;

import com.polimi.ckb.battle.battleService.entity.EducatorEntity;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link com.polimi.ckb.battle.battleService.entity.BattleEntity}
 */
@Value
@Builder
@Data
@AllArgsConstructor
public class BattleDto implements Serializable {
    String name;
    String description;
    EducatorEntity creatorId;
    String registrationDeadline;
    String submissionDeadline;
    Long tournamentID;
}