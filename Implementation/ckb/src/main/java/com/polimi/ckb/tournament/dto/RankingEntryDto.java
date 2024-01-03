package com.polimi.ckb.tournament.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RankingEntryDto {
    private Long studentId;
    private Integer scoreValue;
}
