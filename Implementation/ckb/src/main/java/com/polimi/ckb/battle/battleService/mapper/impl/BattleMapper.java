package com.polimi.ckb.battle.battleService.mapper.impl;

import com.polimi.ckb.battle.battleService.dto.CreateBattleDto;
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
public class BattleMapper implements Mapper<Battle, CreateBattleDto> {
    private ModelMapper modelMapper;

    @Override
    public CreateBattleDto mapTo(Battle battle) {
        return null;
    }

    @Override
    public Battle mapFrom(CreateBattleDto createBattleDto) {
        return null;
    }
}
