package com.polimi.ckb.battleService.service;

import com.polimi.ckb.battleService.dto.*;
import com.polimi.ckb.battleService.entity.Battle;
import com.polimi.ckb.battleService.entity.StudentGroup;

import java.util.List;

public interface BattleService {
    Battle createBattle(CreateBattleDto createBattleDto, boolean isTest) throws RuntimeException;

    StudentGroup joinBattle(StudentJoinBattleDto studentDto);

    StudentGroup leaveBattle(StudentLeaveBattleDto studentDto);

    Battle changeBattleStatus(ChangeBattleStatusDto changeBattleStatusDto);

    void calculateTemporaryScore(NewPushDto newPushDto);

    List<Battle> getBattlesByTournamentId(GetBattleDto getBattleDto);

    void saveRepositoryUrl(SaveRepositoryLinkDto saveRepositoryLinkDto);

    void deleteBattle(DeleteBattleDto deleteBattleDto);

    void uploadYamlFileForNotifications();
}
