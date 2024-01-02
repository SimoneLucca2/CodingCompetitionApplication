package com.polimi.ckb.battle.battleService.service.impl;

import com.polimi.ckb.battle.battleService.config.BattleStatus;
import com.polimi.ckb.battle.battleService.dto.CreateBattleDto;
import com.polimi.ckb.battle.battleService.entity.Battle;
import com.polimi.ckb.battle.battleService.exception.BattleAlreadyExistException;
import com.polimi.ckb.battle.battleService.repository.BattleRepository;
import com.polimi.ckb.battle.battleService.service.BattleService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BattleServiceImpl implements BattleService {
    private final BattleRepository battleRepository;

    @Transactional
    public Battle saveBattle(CreateBattleDto createBattleDto) throws BattleAlreadyExistException {
        battleRepository.deleteBattleByName("test battle");
        List<Battle> battleWithinSameTournament = battleRepository.findByTournamentID(createBattleDto.getTournamentID());

        if(battleWithinSameTournament != null){
            for(Battle battle : battleWithinSameTournament) {
                if (battle.getName().equals(createBattleDto.getName())) {
                    throw new BattleAlreadyExistException();
                }
            }
        }
        return battleRepository.save(convertToEntity(createBattleDto));
    }

    //TODO: maybe put this inside a mapper class
    private Battle convertToEntity(CreateBattleDto createBattleDto){
        return Battle.builder()
                .name(createBattleDto.getName())
                .description(createBattleDto.getDescription())
                .registrationDeadline(createBattleDto.getRegistrationDeadline())
                .submissionDeadline(createBattleDto.getSubmissionDeadline())
                .status(BattleStatus.PRE_BATTLE)
                .creatorId(createBattleDto.getCreatorId())
                .tournamentID(createBattleDto.getTournamentID())
                .maxGroupSize(createBattleDto.getMaxGroupSize())
                .minGroupSize(createBattleDto.getMinGroupSize())
                //.repoLink(null)
                .build();
    }
}
