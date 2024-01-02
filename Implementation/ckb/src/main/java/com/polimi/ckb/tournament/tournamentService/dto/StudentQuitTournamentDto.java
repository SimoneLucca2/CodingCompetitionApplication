package com.polimi.ckb.tournament.tournamentService.dto;

import com.polimi.ckb.tournament.tournamentService.utility.messageValidator.annotation.StudentExists;
import com.polimi.ckb.tournament.tournamentService.utility.messageValidator.annotation.StudentInSpecifiedTournament;
import com.polimi.ckb.tournament.tournamentService.utility.messageValidator.annotation.TournamentExists;
import com.polimi.ckb.tournament.tournamentService.utility.messageValidator.annotation.TournamentInPrepareState;
import lombok.Getter;

import javax.validation.constraints.NotBlank;

@Getter
@StudentInSpecifiedTournament
public class StudentQuitTournamentDto {

    @NotBlank(message = "the tournament id cannot be blank")
    @TournamentExists
    @TournamentInPrepareState
    private Long tournamentId;

    @NotBlank(message = "the student id cannot be blank")
    @StudentExists
    private Long studentId;

}
