package com.polimi.ckb.tournament.dto;

import com.polimi.ckb.tournament.utility.messageValidator.annotation.StudentExists;
import com.polimi.ckb.tournament.utility.messageValidator.annotation.StudentInSpecifiedTournament;
import com.polimi.ckb.tournament.utility.messageValidator.annotation.TournamentExists;
import com.polimi.ckb.tournament.utility.messageValidator.annotation.TournamentInPrepareState;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@StudentInSpecifiedTournament
public class StudentQuitTournamentDto implements Serializable {

    @NotBlank(message = "the tournament id cannot be blank")
    @TournamentExists
    @TournamentInPrepareState
    private Long tournamentId;

    @NotBlank(message = "the student id cannot be blank")
    @StudentExists
    private Long studentId;

}
