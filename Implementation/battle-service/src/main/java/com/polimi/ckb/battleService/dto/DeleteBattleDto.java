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
public class DeleteBattleDto implements Serializable {
    @NotBlank(message = "Battle_id cannot be blank")
    private Long battleId;
}
