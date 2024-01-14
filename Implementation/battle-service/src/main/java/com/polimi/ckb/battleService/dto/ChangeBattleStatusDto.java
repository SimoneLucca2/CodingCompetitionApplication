package com.polimi.ckb.battleService.dto;

import com.polimi.ckb.battleService.config.BattleStatus;
import com.polimi.ckb.battleService.entity.Battle;
import com.polimi.ckb.battleService.utility.messageValidator.annotation.BattleExists;
import com.polimi.ckb.battleService.utility.messageValidator.annotation.EducatorExists;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link Battle}
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangeBattleStatusDto implements Serializable {
        @BattleExists
        private Long battleId;

        @EducatorExists
        private Long educatorId;

        private BattleStatus status;
}
