package com.polimi.ckb.battleService.dto;

import com.polimi.ckb.battleService.entity.Battle;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * DTO for {@link Battle}
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class CreatedBattleDto implements Serializable {
    private Long battleId;
    private String name;
    private String description;
    @NotBlank(message = "Creator cannot be blank")
    private Long creatorId;
    private String registrationDeadline;
    private String submissionDeadline;
    private Long tournamentId;
    private int maxGroupSize;
    private int minGroupSize;

    public static CreatedBattleDto from(Battle battle) {
        return CreatedBattleDto.builder()
                .battleId(battle.getBattleId())
                .name(battle.getName())
                .description(battle.getDescription())
                .creatorId(battle.getCreator().getEducatorId())
                .registrationDeadline(battle.getRegistrationDeadline())
                .submissionDeadline(battle.getSubmissionDeadline())
                .tournamentId(battle.getTournamentId())
                .maxGroupSize(battle.getMaxGroupSize())
                .minGroupSize(battle.getMinGroupSize())
                .build();
    }
}
