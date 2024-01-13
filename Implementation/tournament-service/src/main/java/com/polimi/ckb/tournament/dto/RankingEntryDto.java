package com.polimi.ckb.tournament.dto;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class RankingEntryDto implements Serializable {
    private Long studentId;
    private Integer scoreValue;
}
