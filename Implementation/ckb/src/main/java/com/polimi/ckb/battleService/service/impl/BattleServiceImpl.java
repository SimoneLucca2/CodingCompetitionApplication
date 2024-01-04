package com.polimi.ckb.battleService.service.impl;

import com.polimi.ckb.battleService.config.BattleStatus;
import com.polimi.ckb.battleService.dto.CreateBattleDto;
import com.polimi.ckb.battleService.entity.Battle;
import com.polimi.ckb.battleService.exception.BattleAlreadyExistException;
import com.polimi.ckb.battleService.repository.BattleRepository;
import com.polimi.ckb.battleService.repository.EducatorRepository;
import com.polimi.ckb.battleService.service.BattleService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class BattleServiceImpl implements BattleService {
    private final BattleRepository battleRepository;
    private final EducatorRepository educatorRepository;

    @Transactional
    public Battle saveBattle(CreateBattleDto createBattleDto) throws BattleAlreadyExistException {
        List<Battle> battleWithinSameTournament = battleRepository.findByTournamentId(createBattleDto.getTournamentId());

        //TODO: check the existence of the creator and of the tournament

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
                .creatorId(educatorRepository
                        .findById(createBattleDto.getCreatorId())
                        .orElseThrow(() -> new RuntimeException("Educator not found")))
                .tournamentId(createBattleDto.getTournamentId())
                .maxGroupSize(createBattleDto.getMaxGroupSize())
                .minGroupSize(createBattleDto.getMinGroupSize())
                .build();
    }
}
