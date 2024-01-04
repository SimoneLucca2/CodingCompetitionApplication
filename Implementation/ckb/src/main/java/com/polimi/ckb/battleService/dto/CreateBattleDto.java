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
}