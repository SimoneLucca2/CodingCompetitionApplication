package com.polimi.ckb.battle.battleService.dto;

import com.polimi.ckb.battle.battleService.entity.Educator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.Value;

import java.io.Serializable;

/**
 * DTO for {@link Educator}
 */

@Builder
@Data
@AllArgsConstructor
@Value
public class EducatorDto implements Serializable {
    Long educatorId;
}