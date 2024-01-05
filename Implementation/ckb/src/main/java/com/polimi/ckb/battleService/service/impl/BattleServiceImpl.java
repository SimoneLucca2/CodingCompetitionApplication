package com.polimi.ckb.battleService.service.impl;

import com.polimi.ckb.battleService.config.BattleStatus;
import com.polimi.ckb.battleService.dto.CreateBattleDto;
import com.polimi.ckb.battleService.entity.Battle;
import com.polimi.ckb.battleService.exception.BattleAlreadyExistException;
import com.polimi.ckb.battleService.exception.BattleDeadlinesOverlapException;
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
    public Battle saveBattle(CreateBattleDto createBattleDto) throws RuntimeException {
        List<Battle> battleWithinSameTournament = battleRepository.findByTournamentId(createBattleDto.getTournamentId());

        //TODO: check the existence of the creator and of the tournament with HTTP request to TournamentService

        //check if it already exists a battle with the same name in the tournament
        if(battleWithinSameTournament != null){
            for(Battle battle : battleWithinSameTournament) {
                if (battle.getName().equals(createBattleDto.getName())) {
                    throw new BattleAlreadyExistException();
                }
            }

            for(Battle battle : battleWithinSameTournament) {
                if(battle.getStatus().equals(BattleStatus.PRE_BATTLE) || battle.getStatus().equals(BattleStatus.BATTLE)){
                    final boolean check1 = createBattleDto.getRegistrationDeadline().compareTo(battle.getRegistrationDeadline()) < 0 &&
                                            createBattleDto.getSubmissionDeadline().compareTo(battle.getSubmissionDeadline()) < 0 &&
                                            createBattleDto.getSubmissionDeadline().compareTo(battle.getRegistrationDeadline()) <= 0;

                    final boolean check2 = createBattleDto.getRegistrationDeadline().compareTo(battle.getRegistrationDeadline()) > 0 &&
                                            createBattleDto.getSubmissionDeadline().compareTo(battle.getSubmissionDeadline()) > 0 &&
                                            createBattleDto.getRegistrationDeadline().compareTo(battle.getSubmissionDeadline()) >= 0;

                    if(!(check1 || check2)){
                        throw new BattleDeadlinesOverlapException();
                    }
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
