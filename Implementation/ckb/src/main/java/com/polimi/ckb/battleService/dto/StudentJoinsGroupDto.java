package com.polimi.ckb.battleService.dto;

import com.polimi.ckb.battleService.entity.StudentGroup;
import com.polimi.ckb.battleService.utility.messageValidator.annotation.GroupExists;
import com.polimi.ckb.battleService.utility.messageValidator.annotation.StudentExists;
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
    @StudentExists
    Long studentId;

    @GroupExists
    Long groupId;
}
