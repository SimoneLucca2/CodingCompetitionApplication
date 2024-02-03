package com.polimi.ckb.battleService.dto;

import com.polimi.ckb.battleService.utility.messageValidator.annotation.BattleExists;
import com.polimi.ckb.battleService.utility.messageValidator.annotation.StudentExists;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveGroupRepositoryLinkDto implements Serializable {
    @StudentExists
    private Long studentId;

    @BattleExists
    private Long battleId;

    @NotBlank
    private String clonedRepositoryLink;
}
