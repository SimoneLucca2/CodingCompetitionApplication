package com.polimi.ckb.battle.battleService.service.impl;

import com.polimi.ckb.battle.battleService.config.BattleStatus;
import com.polimi.ckb.battle.battleService.dto.CreateBattleMessage;
import com.polimi.ckb.battle.battleService.entity.BattleEntity;
import com.polimi.ckb.battle.battleService.repository.BattleRepository;
import com.polimi.ckb.battle.battleService.service.BattleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BattleServiceImpl implements BattleService {
    private final BattleRepository battleRepository;

    @Transactional
    public BattleEntity saveBattle(CreateBattleMessage msg){
        Optional<BattleEntity> maybeBattle = battleRepository.findByName(msg.name());
        //TODO: battle must be unique within a tournament
        return battleRepository.save(convertToEntity(msg));
    }

    //TODO: maybe put this inside a mapper class
    private BattleEntity convertToEntity(CreateBattleMessage createBattleMessage){
        return BattleEntity.builder()
                .name(createBattleMessage.name())
                .description(createBattleMessage.description())
                .registrationDeadline(createBattleMessage.registrationDeadline())
                .submissionDeadline(createBattleMessage.submissionDeadline())
                .status(BattleStatus.PRE_BATTLE)
                .creatorId(createBattleMessage.creatorId())
                .tournamentID(createBattleMessage.tournamentId())
                .repoLink(null)
                .build();
    }
}
