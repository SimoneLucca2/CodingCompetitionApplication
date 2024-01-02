package com.polimi.ckb.tournament.tournamentService.dto;

import com.polimi.ckb.tournament.tournamentService.utility.messageValidator.annotation.StudentExists;
import com.polimi.ckb.tournament.tournamentService.utility.messageValidator.annotation.TournamentExists;
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
    @StudentExists
    private Long studentId;

    @NotBlank(message = "Score cannot be null")
    private Integer score;

    @NotBlank(message = "tournament id can not be null")
    @TournamentExists
    private Long tournamentId;

}
