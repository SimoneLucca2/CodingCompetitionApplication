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
public class InternalChangeTournamentStatusDto {

    @NotNull(message = "Status cannot be null")
    @NotEmpty
    @NotBlank
    private TournamentStatus status;

}