package com.polimi.ckb.battle.battleService.service.impl;

import com.polimi.ckb.battle.battleService.config.BattleStatus;
import com.polimi.ckb.battle.battleService.dto.BattleDto;
import com.polimi.ckb.battle.battleService.entity.Battle;
import com.polimi.ckb.battle.battleService.exception.BattleAlreadyExistException;
import com.polimi.ckb.battle.battleService.repository.BattleRepository;
import com.polimi.ckb.battle.battleService.service.BattleService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class BattleServiceImpl implements BattleService {
    private final BattleRepository battleRepository;

    @Transactional
    public Battle saveBattle(BattleDto battleDto) throws BattleAlreadyExistException {
        List<Battle> battleWithinSameTournament = battleRepository.findByTournamentID(battleDto.getTournamentID());

        if(battleWithinSameTournament != null){
            for(Battle battle : battleWithinSameTournament) {
                if (battle.getName().equals(battleDto.getName())) {
                    throw new BattleAlreadyExistException();
                }
            }
        }
        return battleRepository.save(convertToEntity(battleDto));
    }

    //TODO: maybe put this inside a mapper class
    private Battle convertToEntity(BattleDto battleDto){
        return Battle.builder()
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
