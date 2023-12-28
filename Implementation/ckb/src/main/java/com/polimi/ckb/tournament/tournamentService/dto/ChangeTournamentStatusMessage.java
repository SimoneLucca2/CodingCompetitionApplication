package com.polimi.ckb.tournament.tournamentService.dto;

import com.polimi.ckb.tournament.tournamentService.config.TournamentStatus;

import javax.validation.constraints.NotNull;

public record ChangeTournamentStatusMessage(
        @NotNull(message = "EducatorId cannot be null")
        Long educatorId, //id of the educator who is requesting the change
        @NotNull(message = "TournamentId cannot be null")
        Long tournamentId,
        @NotNull(message = "Status cannot be null")
        TournamentStatus status
) {
}
