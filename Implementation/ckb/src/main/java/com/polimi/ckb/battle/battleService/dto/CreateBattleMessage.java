package com.polimi.ckb.battle.battleService.dto;

public record CreateBattleMessage(
        String name,
        String description,
        Long creatorId,
        String registrationDeadline,
        String submissionDeadline,
        Long tournamentId
) {
}
