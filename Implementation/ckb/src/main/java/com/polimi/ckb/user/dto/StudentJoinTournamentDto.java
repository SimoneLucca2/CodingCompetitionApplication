package com.polimi.ckb.user.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor
@Setter
@AllArgsConstructor
public class StudentJoinTournamentDto {
    @NotBlank(message = "student id cannot be null")
    private Long studentId;
    @NotBlank(message = "tournament id cannot be null")
    private Long tournamentId;
}
