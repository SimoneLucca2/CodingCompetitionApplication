package com.polimi.ckb.tournament.tournamentService.dto;

import com.polimi.ckb.tournament.tournamentService.config.TournamentStatus;
import lombok.Builder;
import lombok.Getter;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;

/**
 * DTO for {@link com.polimi.ckb.tournament.tournamentService.entity.Tournament}
 */

@Getter
@Builder
public class ChangeTournamentStatusDto {

    @NotNull(message = "EducatorId cannot be null")
    @NotEmpty
    @NotBlank
    private Long educatorId; //id of the educator who is requesting the change

    @NotNull(message = "TournamentId cannot be null")
    @NotEmpty
    @NotBlank
    private Long tournamentId;

    @NotNull(message = "Status cannot be null")
    @NotEmpty
    @NotBlank
    private TournamentStatus status;

}