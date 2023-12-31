package com.polimi.ckb.battle.battleService.dto;

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
public class BattleDto implements Serializable {
    String name;
    String description;
    EducatorDto creatorId;
    String registrationDeadline;
    String submissionDeadline;
    Long tournamentID;
}