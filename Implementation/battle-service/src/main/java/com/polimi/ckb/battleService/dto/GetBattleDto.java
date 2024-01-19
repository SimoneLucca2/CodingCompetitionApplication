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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class GetBattleDto implements Serializable {
    @NotBlank(message = "Tournament_id cannot be blank")
    private Long tournamentId;
}
