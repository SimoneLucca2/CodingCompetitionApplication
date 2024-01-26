package com.polimi.ckb.tournament.entity.compositeKeys;

import jakarta.persistence.Embeddable;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Embeddable
@Data
@NoArgsConstructor
@AllArgsConstructor
public class ScoreId implements Serializable {
    private Long studentId;
    private Long tournamentId;
}
