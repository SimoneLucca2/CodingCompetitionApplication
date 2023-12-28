package com.polimi.ckb.tournament.tournamentService.dto;

import com.polimi.ckb.tournament.tournamentService.utility.messageValidator.annotation.ValidRegistrationDeadline;

import javax.validation.constraints.NotEmpty;

public record CreateTournamentMessage(

        @NotEmpty(message = "Name cannot be empty")
        String name,

        @NotEmpty(message = "Creator cannot be empty")
        String creator,

        @NotEmpty(message = "Registration Deadline cannot be empty")
        @ValidRegistrationDeadline
        String registrationDeadline,

        @NotEmpty(message = "Status cannot be empty")
        String status
) {}