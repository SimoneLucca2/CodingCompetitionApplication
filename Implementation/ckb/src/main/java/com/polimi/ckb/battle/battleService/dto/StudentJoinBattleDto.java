package com.polimi.ckb.battle.battleService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link StudentJoinBattleDto}
 */

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class StudentJoinBattleDto {
    Long studentId;
    Long battleId;
}
