package com.polimi.ckb.battleService.service;

import com.polimi.ckb.battleService.dto.*;
import com.polimi.ckb.battleService.entity.Battle;
import com.polimi.ckb.battleService.entity.StudentGroup;
import com.polimi.ckb.battleService.exception.BattleAlreadyExistException;

public interface BattleService {
    Battle createBattle(CreateBattleDto msg) throws BattleAlreadyExistException;

    StudentGroup joinBattle(StudentJoinBattleDto studentDto);

    StudentGroup leaveBattle(StudentLeaveBattleDto studentDto);

    Battle changeBattleStatus(ChangeBattleStatusDto changeBattleStatusDto);

    void calculateTemporaryScore(NewPushDto newPushDto);
}
