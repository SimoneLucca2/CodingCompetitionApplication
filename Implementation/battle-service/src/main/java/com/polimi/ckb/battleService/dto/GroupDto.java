package com.polimi.ckb.battleService.dto;

import com.polimi.ckb.battleService.utility.messageValidator.annotation.GroupExists;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link com.polimi.ckb.battleService.entity.StudentGroup}
 */

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class GroupDto implements Serializable {
    @GroupExists
    private Long groupId;
}
