package com.polimi.ckb.battleService.dto;

import com.polimi.ckb.battleService.entity.StudentGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

/**
 * DTO for {@link StudentGroup}
 */

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class GroupScoreDto implements Serializable {
    private Long groupId;
    private Float score;
    private String clonedRepositoryLink;
}
