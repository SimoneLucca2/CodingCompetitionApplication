package com.polimi.ckb.tournament.tournamentService.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.polimi.ckb.tournament.tournamentService.dto.CreateTournamentMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
public class TournamentCreationKafkaProducer {

    private static final String TOPIC = "tournament.creation";
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Autowired
    public TournamentCreationKafkaProducer(KafkaTemplate<String, Object> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    @Retryable(maxAttempts = 5, backoff = @Backoff(delay = 1000, multiplier = 1.5))
    public void sendTournamentCreationMessage(CreateTournamentMessage message) throws JsonProcessingException {
        String jsonMessage = objectMapper.writeValueAsString(message);
        kafkaTemplate.send(TOPIC, jsonMessage);
    }
}
