package com.polimi.ckb.tournament.utility.entityConverter;

import com.polimi.ckb.tournament.config.TournamentStatus;
import com.polimi.ckb.tournament.dto.CreateTournamentDto;
import com.polimi.ckb.tournament.entity.Tournament;

public class CreateTournamentDtoToTournament implements EntityConverter {
    public static Tournament convertToEntity(CreateTournamentDto createTournamentDto) {
        return Tournament.builder()
                .name(createTournamentDto.getName())
                .description(createTournamentDto.getDescription())
                .creatorId(createTournamentDto.getCreatorId())
                .registrationDeadline(createTournamentDto.getRegistrationDeadline())
                .status(TournamentStatus.PREPARATION)
                .build();
    }
}
