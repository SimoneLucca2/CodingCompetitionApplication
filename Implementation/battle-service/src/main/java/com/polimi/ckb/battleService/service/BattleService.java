package com.polimi.ckb.battleService.service;

import com.polimi.ckb.battleService.dto.ChangeBattleStatusDto;
import com.polimi.ckb.battleService.dto.CreateBattleDto;
import com.polimi.ckb.battleService.dto.StudentJoinBattleDto;
import com.polimi.ckb.battleService.dto.StudentLeaveBattleDto;
import com.polimi.ckb.battleService.entity.Battle;
import com.polimi.ckb.battleService.entity.StudentGroup;
import com.polimi.ckb.battleService.exception.BattleAlreadyExistException;

public interface BattleService {
    Battle createBattle(CreateBattleDto msg) throws BattleAlreadyExistException;

    StudentGroup joinBattle(StudentJoinBattleDto studentDto);

    StudentGroup leaveBattle(StudentLeaveBattleDto studentDto);

    Battle changeBattleStatus(ChangeBattleStatusDto changeBattleStatusDto);
}
