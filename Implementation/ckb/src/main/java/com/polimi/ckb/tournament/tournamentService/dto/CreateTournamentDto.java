package com.polimi.ckb.tournament.tournamentService.dto;

import com.polimi.ckb.tournament.tournamentService.config.TournamentStatus;
import com.polimi.ckb.tournament.tournamentService.utility.messageValidator.annotation.ValidRegistrationDeadline;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * DTO for {@link com.polimi.ckb.tournament.tournamentService.entity.Tournament}
 */
@Value
@Builder
public class CreateTournamentDto implements Serializable {

        @NotBlank(message = "name must not be blank")
        String name; // name of the tournament

        @NotBlank(message = "Creator ID must not be blank")
        Long creatorId;

        @NotBlank(message = "Registration deadline must not be blank")
        @ValidRegistrationDeadline
        String registrationDeadline;

        @NotNull
        TournamentStatus status;
}