package com.polimi.ckb.tournament.tournamentService.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Setter
@Getter
@Builder
@AllArgsConstructor
public class GetTournamentDto {
    @NotBlank(message = "TournamentId cannot be null")
    private Long tournamentId;
}
