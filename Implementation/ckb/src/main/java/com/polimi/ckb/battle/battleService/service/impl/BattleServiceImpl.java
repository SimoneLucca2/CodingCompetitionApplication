package com.polimi.ckb.battle.battleService.service.impl;

import com.polimi.ckb.battle.battleService.config.BattleStatus;
import com.polimi.ckb.battle.battleService.dto.BattleDto;
import com.polimi.ckb.battle.battleService.entity.BattleEntity;
import com.polimi.ckb.battle.battleService.repository.BattleEntityRepository;
import com.polimi.ckb.battle.battleService.service.BattleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.swing.text.IconView;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class BattleServiceImpl implements BattleService {
    private final BattleEntityRepository battleEntityRepository;

    @Transactional
    public BattleEntity saveBattle(BattleDto battleDto){
        Optional<BattleEntity> maybeBattle = battleEntityRepository.findByName(battleDto.getName());
        //TODO: battle must be unique within a tournament
        return battleEntityRepository.save(convertToEntity(battleDto));
    }

    //TODO: maybe put this inside a mapper class
    private BattleEntity convertToEntity(BattleDto battleDto){
        return BattleEntity.builder()
                .name(battleDto.getName())
                .description(battleDto.getDescription())
                .registrationDeadline(battleDto.getRegistrationDeadline())
                .submissionDeadline(battleDto.getSubmissionDeadline())
                .status(BattleStatus.PRE_BATTLE)
                .creatorId(battleDto.getCreatorId())
                .tournamentID(battleDto.getTournamentID())
                //.repoLink(null)
                .build();
    }
}
