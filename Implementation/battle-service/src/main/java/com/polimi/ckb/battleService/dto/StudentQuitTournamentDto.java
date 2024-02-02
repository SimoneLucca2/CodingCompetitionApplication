package com.polimi.ckb.battleService.dto;


import com.polimi.ckb.battleService.utility.messageValidator.annotation.StudentExists;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
public class StudentQuitTournamentDto implements Serializable {

    @NotBlank(message = "the tournament id cannot be blank")
    private Long tournamentId;

    @NotBlank(message = "the student id cannot be blank")
    @StudentExists
    private Long studentId;
}
