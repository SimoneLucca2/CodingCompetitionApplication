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
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CreateGroupDto implements Serializable {
    private Long battleId;
    private Long studentId;
}
