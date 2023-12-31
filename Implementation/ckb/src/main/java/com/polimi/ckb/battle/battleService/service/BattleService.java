package com.polimi.ckb.battle.battleService.service;

import com.polimi.ckb.battle.battleService.dto.BattleDto;
import com.polimi.ckb.battle.battleService.entity.BattleEntity;

public interface BattleService {
    BattleEntity saveBattle(BattleDto msg);
}
