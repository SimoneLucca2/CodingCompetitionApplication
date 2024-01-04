package com.polimi.ckb.battleService.mapper.impl;

import com.polimi.ckb.battleService.dto.CreateBattleDto;
import com.polimi.ckb.battleService.entity.Battle;
import com.polimi.ckb.battleService.mapper.Mapper;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

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
