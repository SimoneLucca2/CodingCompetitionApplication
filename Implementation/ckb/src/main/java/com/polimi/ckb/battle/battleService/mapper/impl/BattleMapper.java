package com.polimi.ckb.battle.battleService.mapper.impl;

import com.polimi.ckb.battle.battleService.dto.BattleDto;
import com.polimi.ckb.battle.battleService.entity.Battle;
import com.polimi.ckb.battle.battleService.mapper.Mapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;
import org.modelmapper.ModelMapper;

@Component
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class BattleMapper implements Mapper<Battle, BattleDto> {
    private ModelMapper modelMapper;

    @Override
    public BattleDto mapTo(Battle battle) {
        return null;
    }

    @Override
    public Battle mapFrom(BattleDto battleDto) {
        return null;
    }
}
