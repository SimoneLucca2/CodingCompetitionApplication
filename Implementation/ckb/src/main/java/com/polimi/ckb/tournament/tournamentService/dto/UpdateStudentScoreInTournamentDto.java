package com.polimi.ckb.tournament.tournamentService.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

/**
 * DTO for {@link com.polimi.ckb.tournament.tournamentService.entity.Tournament}
 */

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
