package com.polimi.ckb.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Builder
@NoArgsConstructor
@Setter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewTournamentDto implements Serializable {
    @NotBlank(message = "EducatorId cannot be null")
    private Long educatorId; //id of the educator who is creating the tournament

    @NotBlank(message = "registration deadline cannot be blank")
    private String registrationDeadline;

    @NotBlank(message = "Tournament id cannot be null")
    private Long tournamentId;
}
