package com.polimi.ckb.user.utility.entityConverter;

import com.polimi.ckb.user.dto.NewTournamentDto;
import com.polimi.ckb.user.entity.Tournament;

public class NewTournamentDtoToTournament implements EntityConverter {


    public static Tournament convertToEntity(NewTournamentDto msg) {
        return Tournament.builder().tournamentId(msg.getTournamentId()).build();
    }

}
