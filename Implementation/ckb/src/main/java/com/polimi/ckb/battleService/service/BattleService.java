package com.polimi.ckb.battleService.service;

import com.polimi.ckb.battleService.dto.CreateBattleDto;
import com.polimi.ckb.battleService.entity.Battle;
import com.polimi.ckb.battleService.exception.BattleAlreadyExistException;

public interface BattleService {
    Battle saveBattle(CreateBattleDto msg) throws BattleAlreadyExistException;
}
