package com.polimi.ckb.timeServer.service.kafka;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.polimi.ckb.timeServer.dto.ChangeBattleStatusDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class BattleChangeStatusKafkaProducer {
    private static final String TOPIC = "battle.lifecycle.change";
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Retryable(maxAttempts = 5, backoff = @Backoff(delay = 1000, multiplier = 1.5))
    public void sendBattleActiveMessage(ChangeBattleStatusDto msg) throws JsonProcessingException {
        String jsonMessage = objectMapper.writeValueAsString(msg);
        kafkaTemplate.send(TOPIC, jsonMessage);
    }
}
