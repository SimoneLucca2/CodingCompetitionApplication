package com.polimi.ckb.battle.battleService.dto;

import com.polimi.ckb.battle.battleService.entity.Educator;
import lombok.*;

import java.io.Serializable;

/**
 * DTO for {@link Educator}
 */

@Builder
@Data
@AllArgsConstructor
@NoArgsConstructor
public class EducatorDto implements Serializable {
    Long educatorId;
}