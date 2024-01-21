package com.polimi.ckb.user.utility.entityConverter;

import com.polimi.ckb.user.dto.CreateBattleDto;
import com.polimi.ckb.user.dto.NewTournamentDto;
import com.polimi.ckb.user.entity.Battle;
import com.polimi.ckb.user.entity.Tournament;
import com.polimi.ckb.user.service.TournamentService;

public class CreateBattleDtoToBattle implements EntityConverter {

    public static Battle toEntity(CreateBattleDto msg) {
        return Battle.builder()
                .battleId(msg.getBattleId())
                .tournament(Tournament.builder().tournamentId(msg.getTournamentId()).build())
                .build();
    }

}
