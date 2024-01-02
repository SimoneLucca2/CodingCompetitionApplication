package com.polimi.ckb.tournament.tournamentService.utility.entityConverter;

import com.polimi.ckb.tournament.tournamentService.dto.NewUserDto;
import com.polimi.ckb.tournament.tournamentService.entity.Educator;

public class NewUserDtoToUser implements EntityConverter {

    public static Educator convertToEntity(NewUserDto msg) {
        return Educator.builder().educatorId(msg.getUserId()).build();
    }

}
