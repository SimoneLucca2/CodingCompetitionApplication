package com.polimi.ckb.tournament.tournamentService.utility.entityConverter;

import com.polimi.ckb.tournament.tournamentService.config.TournamentStatus;
import com.polimi.ckb.tournament.tournamentService.dto.CreateTournamentDto;
import com.polimi.ckb.tournament.tournamentService.entity.Tournament;

public class CreateTournamentDtoToTournament implements EntityConverter {
    public static Tournament convertToEntity(CreateTournamentDto createTournamentDto) {
        return Tournament.builder()
                .name(createTournamentDto.getName())
                .creatorId(createTournamentDto.getCreatorId())
                .registrationDeadline(createTournamentDto.getRegistrationDeadline())
                .status(TournamentStatus.PREPARATION)
                .build();
    }
}
