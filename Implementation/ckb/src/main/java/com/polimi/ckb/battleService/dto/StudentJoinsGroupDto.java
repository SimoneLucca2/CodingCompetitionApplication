package com.polimi.ckb.battleService.dto;

import com.polimi.ckb.battleService.entity.StudentGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link StudentGroup}
 */

@NoArgsConstructor
@Data
@AllArgsConstructor
@Builder
public class StudentJoinsGroupDto {
    Long studentId;
    Long groupId;
}
