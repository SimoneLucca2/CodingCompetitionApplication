package com.polimi.ckb.tournament.service.kafkaProducer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.polimi.ckb.tournament.dto.AddEducatorDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
//TODO to remove
public class AddEducatorKafkaProducer {
    private static final String TOPIC = "tournament.educator.add";
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Async
    @Retryable(maxAttempts = 5, backoff = @Backoff(delay = 1000, multiplier = 1.5))
    public void sendAddedEducatorMessage(AddEducatorDto msg) throws JsonProcessingException {
        String jsonMessage = objectMapper.writeValueAsString(msg);
        kafkaTemplate.send(TOPIC, jsonMessage);
    }
}
