package com.polimi.ckb.battleService.dto;

import com.polimi.ckb.battleService.entity.Battle;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * DTO for {@link Battle}
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
public class CreateBattleDto implements Serializable {
    String name;
    String description;
    @NotBlank(message = "Creator cannot be blank")
    Long creatorId;
    String registrationDeadline;
    String submissionDeadline;
    Long tournamentId;
    int maxGroupSize;
    int minGroupSize;

    public static CreateBattleDto from(Battle battle) {
        return CreateBattleDto.builder()
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