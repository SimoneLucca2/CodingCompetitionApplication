package com.polimi.ckb.battleService.service;

import com.polimi.ckb.battleService.dto.StudentJoinBattleDto;
import com.polimi.ckb.battleService.entity.StudentGroup;

public interface StudentService {

    StudentGroup joinBattle(StudentJoinBattleDto studentDto);

    void leaveBattle(StudentJoinBattleDto studentDto);
}
