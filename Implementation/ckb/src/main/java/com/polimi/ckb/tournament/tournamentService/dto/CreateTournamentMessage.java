package com.polimi.ckb.tournament.tournamentService.dto;

import com.polimi.ckb.tournament.tournamentService.utility.messageValidator.annotation.ValidRegistrationDeadline;
import com.polimi.ckb.tournament.tournamentService.utility.messageValidator.annotation.ValidStatus;

import javax.validation.constraints.NotNull;
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
        @ValidStatus
        String status
) {}