package com.polimi.ckb.tournament.tournamentService.dto;

import com.polimi.ckb.tournament.tournamentService.config.TournamentStatus;
import com.polimi.ckb.tournament.tournamentService.utility.messageValidator.annotation.ValidRegistrationDeadline;
import lombok.Builder;
import lombok.Value;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * DTO for {@link com.polimi.ckb.tournament.tournamentService.entity.Tournament}
 */
@Value
@Builder
public class CreateTournamentDto implements Serializable {
        @NotNull
        @NotEmpty
        @NotBlank
        String name;

        @NotNull
        @NotEmpty
        @NotBlank
        String creatorId;

        @NotNull
        @NotEmpty
        @ValidRegistrationDeadline
        String registrationDeadline;

        @NotNull
        TournamentStatus status;
}