package com.polimi.ckb.battle.battleService.mapper.impl;

import com.polimi.ckb.battle.battleService.dto.BattleDto;
import com.polimi.ckb.battle.battleService.entity.BattleEntity;
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
public class BattleMapper implements Mapper<BattleEntity, BattleDto> {
    private ModelMapper modelMapper;

    @Override
    public BattleDto mapTo(BattleEntity battleEntity) {
        return null;
    }

    @Override
    public BattleEntity mapFrom(BattleDto battleDto) {
        return null;
    }
}
