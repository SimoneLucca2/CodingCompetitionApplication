package com.polimi.ckb.tournament.tournamentService.dto;

import javax.validation.constraints.NotNull;

public record AddEducatorMessage(
        @NotNull(message = "RequesterId cannot be null")
        Long requesterId,
        @NotNull(message = "TournamentId cannot be null")
        Long tournamentId,
        @NotNull(message = "EducatorId cannot be null")
        Long educatorId) {}
