package com.polimi.ckb.battle.battleService.dto;

import com.polimi.ckb.battle.battleService.entity.Battle;
import com.polimi.ckb.battle.battleService.entity.Educator;
import lombok.*;
import java.io.Serializable;

/**
 * DTO for {@link Battle}
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class BattleDto implements Serializable {
    String name;
    String description;
    Educator creatorId;
    String registrationDeadline;
    String submissionDeadline;
    Long tournamentID;
    int maxGroupSize;
    int minGroupSize;
}