package com.polimi.ckb.battleService.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


@Getter
@Builder
@NoArgsConstructor
@Setter
@AllArgsConstructor
public class UpdateStudentScoreInTournamentDto {

    @NotBlank(message = "student id cannot be null")
    private Long studentId;

    @NotBlank(message = "Score cannot be null")
    private Integer score;

    @NotBlank(message = "tournament id can not be null")
    private Long tournamentId;

}
