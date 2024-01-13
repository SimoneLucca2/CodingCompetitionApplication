package com.polimi.ckb.timeServer.dto;


import com.polimi.ckb.timeServer.config.TournamentStatus;
import com.polimi.ckb.timeServer.utility.messageValidator.annotations.ValidRegistrationDeadline;
import lombok.*;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.io.Serializable;

@NoArgsConstructor
@Setter
@Getter
@ToString
@Builder
@AllArgsConstructor
public class CreatedTournamentKafkaDto implements Serializable {

    @NotBlank(message = "Tournament ID must not be blank")
    Long tournamentId;

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