package com.polimi.ckb.tournament.dto;

import com.polimi.ckb.tournament.entity.Tournament;
import com.polimi.ckb.tournament.utility.messageValidator.annotation.StudentExists;
import com.polimi.ckb.tournament.utility.messageValidator.annotation.TournamentExists;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * DTO for {@link Tournament}
 */

@Getter
@Builder
@NoArgsConstructor
@Setter
@AllArgsConstructor
public class UpdateStudentScoreInTournamentDto implements Serializable {

    @NotBlank(message = "student id cannot be null")
    @StudentExists
    private Long studentId;

    @NotBlank(message = "Score cannot be null")
    private Integer score;

    @NotBlank(message = "tournament id can not be null")
    @TournamentExists
    private Long tournamentId;

}
