package com.polimi.ckb.timeServer.service.kafkaConsumer;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.polimi.ckb.timeServer.config.BattleStatus;
import com.polimi.ckb.timeServer.dto.ChangeBattleStatusDto;
import com.polimi.ckb.timeServer.dto.CreateBattleDto;
import com.polimi.ckb.timeServer.dto.CreatedTournamentKafkaDto;
import com.polimi.ckb.timeServer.service.timeServices.BattleCreationTimeService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.polimi.ckb.timeServer.utility.TimeTypeConverter.calculateMillisecondsToDeadline;

@AllArgsConstructor
@Service
public class BattleCreationKafkaConsumer {

    private final ObjectMapper objectMapper;
    private final BattleCreationTimeService battleCreationTimeService;

    @KafkaListener(topics = "battle.creation", groupId = "time-server")
    public void listener(String message) throws JsonProcessingException {
        CreateBattleDto msg = objectMapper.readValue(message, CreateBattleDto.class);

        //when the deadline expires, the battle will be set to the status BATTLE
        msg.setStatus(BattleStatus.BATTLE);
        battleCreationTimeService.setTimer(calculateMillisecondsToDeadline(msg.getRegistrationDeadline()), msg);

        //when the deadline expires, the battle will be set to the status CONSOLIDATION
        msg.setStatus(BattleStatus.CONSOLIDATION);
        battleCreationTimeService.setTimer(calculateMillisecondsToDeadline(msg.getSubmissionDeadline()), msg);
    }

}
