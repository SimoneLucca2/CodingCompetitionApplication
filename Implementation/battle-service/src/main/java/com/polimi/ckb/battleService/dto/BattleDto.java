package com.polimi.ckb.battleService.dto;

import com.polimi.ckb.battleService.config.BattleStatus;
import com.polimi.ckb.battleService.entity.Battle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link Battle}
 */


@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BattleDto implements Serializable{
    private Long battleId;
    private String name;
    private String description;
    private String registrationDeadline;
    private String submissionDeadline;
    private BattleStatus status;
    private int maxGroupSize;
    private int minGroupSize;
    private String repoLink;
}
