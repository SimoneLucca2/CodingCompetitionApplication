package com.polimi.ckb.battleService.service.kafkaProducer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.polimi.ckb.battleService.dto.CreatedBattleDto;
import com.polimi.ckb.battleService.dto.UpdateStudentScoreInTournamentDto;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class BattleScoreKafkaProducer {
    private static final String TOPIC = "battle.student.score";
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Retryable(maxAttempts = 5, backoff = @Backoff(delay = 1000, multiplier = 1.5))
    @Async
    public void sendBattleCreationMessage(UpdateStudentScoreInTournamentDto message) throws JsonProcessingException {
        String jsonMessage = objectMapper.writeValueAsString(message);
        kafkaTemplate.send(TOPIC, jsonMessage);
    }
}
