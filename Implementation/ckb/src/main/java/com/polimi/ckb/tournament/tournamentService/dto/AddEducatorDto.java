package com.polimi.ckb.tournament.tournamentService.dto;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * DTO for {@link com.polimi.ckb.tournament.tournamentService.entity.Educator}
 */

@Getter
@Builder
@AllArgsConstructor
public class AddEducatorDto {

    @NotNull(message = "RequesterId cannot be null")
    @NotEmpty
    @NotBlank
    private Long requesterId;

    @NotNull(message = "TournamentId cannot be null")
    @NotEmpty
    @NotBlank
    private Long tournamentId;

    @NotNull(message = "EducatorId cannot be null")
    @NotEmpty
    @NotBlank
    private Long educatorId;
}