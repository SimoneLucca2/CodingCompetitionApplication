package com.polimi.ckb.battleService.dto;

import com.polimi.ckb.battleService.entity.StudentGroup;
import com.polimi.ckb.battleService.utility.messageValidator.annotation.BattleExists;
import com.polimi.ckb.battleService.utility.messageValidator.annotation.StudentExists;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link StudentGroup}
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class StudentLeaveBattleDto {
    @StudentExists
    private Long studentId;

    @BattleExists
    private Long battleId;
}
