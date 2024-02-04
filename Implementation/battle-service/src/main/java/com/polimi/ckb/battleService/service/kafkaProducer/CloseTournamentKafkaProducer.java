package com.polimi.ckb.battleService.service.kafkaProducer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.polimi.ckb.battleService.dto.InternalChangeTournamentStatusDto;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
@AllArgsConstructor
public class CloseTournamentKafkaProducer {

    private static final String TOPIC = "tournament.lifecycle.close";
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Async
    @Retryable(maxAttempts = 5, backoff = @Backoff(delay = 1000, multiplier = 1.5))
    public void closeTournament(InternalChangeTournamentStatusDto studentJoinBattleDto) throws JsonProcessingException {
        String jsonMessage = objectMapper.writeValueAsString(studentJoinBattleDto);
        kafkaTemplate.send(TOPIC, jsonMessage);
    }
}
