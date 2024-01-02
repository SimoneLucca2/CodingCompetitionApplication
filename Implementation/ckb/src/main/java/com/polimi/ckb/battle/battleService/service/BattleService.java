package com.polimi.ckb.battle.battleService.service;

import com.polimi.ckb.battle.battleService.dto.CreateBattleDto;
import com.polimi.ckb.battle.battleService.entity.Battle;
import com.polimi.ckb.battle.battleService.exception.BattleAlreadyExistException;

public interface BattleService {
    Battle saveBattle(CreateBattleDto msg) throws BattleAlreadyExistException;
}
