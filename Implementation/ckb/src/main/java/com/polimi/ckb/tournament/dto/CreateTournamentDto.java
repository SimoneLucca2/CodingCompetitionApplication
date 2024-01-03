package com.polimi.ckb.tournament.dto;

import com.polimi.ckb.tournament.config.TournamentStatus;
import com.polimi.ckb.tournament.entity.Tournament;
import com.polimi.ckb.tournament.utility.messageValidator.annotation.ValidRegistrationDeadline;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

/**
 * DTO for {@link Tournament}
 */
@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
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