package com.polimi.ckb.user.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor
@Setter
@AllArgsConstructor
public class NewTournamentDto {
    @NotBlank(message = "EducatorId cannot be null")
    private Long educatorId; //id of the educator who is creating the tournament

    @NotBlank(message = "registration deadline cannot be blank")
    private String registrationDeadline;

    @NotBlank(message = "Tournament id cannot be null")
    private Long tournamentId;
}
