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
    private String name;
    private String description;
    @NotBlank(message = "Creator cannot be blank")
    private Long creatorId;
    private String registrationDeadline;
    private String submissionDeadline;
    private Long tournamentId;
    private int maxGroupSize;
    private int minGroupSize;
}