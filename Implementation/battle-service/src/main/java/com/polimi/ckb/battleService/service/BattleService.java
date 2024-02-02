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

    List<Battle> getBattlesByTournamentId(Long tournamentId);

    void saveRepositoryUrl(SaveRepositoryLinkDto saveRepositoryLinkDto);

    void deleteBattle(DeleteBattleDto deleteBattleDto);

    void quitEntireTournament(StudentQuitTournamentDto studentQuitTournamentDto);
}
