package com.polimi.ckb.tournament.utility.entityConverter;

import com.polimi.ckb.tournament.dto.NewUserDto;
import com.polimi.ckb.tournament.entity.Educator;

public class NewUserDtoToEducator implements EntityConverter {

    public static Educator convertToEntity(NewUserDto msg) {
        return Educator.builder().educatorId(msg.getUserId()).build();
    }

}
