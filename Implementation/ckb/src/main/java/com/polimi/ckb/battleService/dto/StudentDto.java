package com.polimi.ckb.battleService.dto;

import com.polimi.ckb.battleService.entity.Student;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * DTO for {@link Student}
 */

@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class StudentDto {
    Long studentId;
}
