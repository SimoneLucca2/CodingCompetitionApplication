package com.polimi.ckb.timeServer.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.polimi.ckb.timeServer.config.BattleStatus;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateBattleDto implements Serializable {
    String name;
    String description;
    String registrationDeadline;
    String submissionDeadline;
    Long tournamentId;
    int maxGroupSize;
    int minGroupSize;

    // Set upon receiving the message
    BattleStatus status;
}