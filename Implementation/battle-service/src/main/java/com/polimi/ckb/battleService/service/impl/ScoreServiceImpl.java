package com.polimi.ckb.battleService.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.polimi.ckb.battleService.dto.UpdateStudentScoreInTournamentDto;
import com.polimi.ckb.battleService.entity.Battle;
import com.polimi.ckb.battleService.entity.StudentGroup;
import com.polimi.ckb.battleService.exception.BattleDoesNotExistException;
import com.polimi.ckb.battleService.repository.BattleRepository;
import com.polimi.ckb.battleService.repository.StudentRepository;
import com.polimi.ckb.battleService.service.kafkaProducer.BattleScoreKafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ScoreServiceImpl {

    private final BattleRepository battleRepository;
    private final BattleScoreKafkaProducer kafkaProducer;

    @Async
    public void sendScoreForEachStudent(Long battleId){
        Battle battle = battleRepository.findById(battleId).orElseThrow(BattleDoesNotExistException::new);
        List<StudentGroup> group = battle.getStudentGroups();
        group.forEach(g ->
                        g.getStudents()
                                .forEach(st -> {
                                    try {
                                        kafkaProducer.sendBattleCreationMessage(
                                                UpdateStudentScoreInTournamentDto.builder()
                                                        .studentId(st.getStudentId())
                                                        .score(Math.round(g.getScore()))
                                                        .tournamentId(battle.getTournamentId())
                                                        .build()
                                        );
                                    } catch (JsonProcessingException e) {
                                        throw new RuntimeException(e);
                                    }
                                })
        );
    }
}
