package com.polimi.ckb.battle.battleService.service.impl;

import com.polimi.ckb.battle.battleService.config.BattleStatus;
import com.polimi.ckb.battle.battleService.dto.BattleDto;
import com.polimi.ckb.battle.battleService.dto.CreateBattleMessage;
import com.polimi.ckb.battle.battleService.entity.BattleEntity;
import com.polimi.ckb.battle.battleService.repository.BattleEntityRepository;
import com.polimi.ckb.battle.battleService.service.BattleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BattleServiceImpl implements BattleService {
    private final BattleEntityRepository battleEntityRepository;

    @Transactional
    public BattleEntity saveBattle(BattleDto battleDto){
        Optional<BattleEntity> maybeBattle = battleEntityRepository.findByName(battleDto.name());
        //TODO: battle must be unique within a tournament
        return battleEntityRepository.save((battleDto));
    }

    //TODO: maybe put this inside a mapper class
    /*private BattleEntity convertToEntity(BattleDto battleDto){
        return BattleEntity.builder()
                .name(battleDto.name())
                .description(battleDto.description())
                .registrationDeadline(battleDto.registrationDeadline())
                .submissionDeadline(battleDto.submissionDeadline())
                .status(BattleStatus.PRE_BATTLE)
                .creatorId(battleDto.creatorId())
                .tournamentID(battleDto.tournamentId())
                .repoLink(null)
                .build();
    }*/
}
