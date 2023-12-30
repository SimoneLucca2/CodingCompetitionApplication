package com.polimi.ckb.battle.battleService.service;

import com.polimi.ckb.battle.battleService.dto.CreateBattleMessage;
import com.polimi.ckb.battle.battleService.entity.BattleEntity;

public interface BattleService {
    BattleEntity saveBattle(CreateBattleMessage msg);
}
