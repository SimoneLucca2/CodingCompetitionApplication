package com.polimi.ckb.battleService.dto;

import com.polimi.ckb.battleService.entity.StudentGroup;
import com.polimi.ckb.battleService.utility.messageValidator.annotation.GroupExists;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * DTO for {@link StudentGroup}
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SaveGroupRepositoryLinkDto implements Serializable {
    @GroupExists
    private Long groupId;

    @NotBlank
    private String clonedRepositoryLink;
}
