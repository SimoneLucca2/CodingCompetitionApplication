package com.polimi.ckb.timeServer.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.polimi.ckb.timeServer.config.BattleStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreatedBattleDto implements Serializable {
    private Long battleId;
    private String name;
    private String description;
    private Long creatorId;
    private String registrationDeadline;
    private String submissionDeadline;
    private Long tournamentId;
    private int maxGroupSize;
    private int minGroupSize;

    // Set upon receiving the message
    private BattleStatus status;
}