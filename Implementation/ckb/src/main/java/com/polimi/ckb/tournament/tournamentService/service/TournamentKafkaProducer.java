package com.polimi.ckb.tournament.tournamentService.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.polimi.ckb.tournament.tournamentService.dto.CreateTournamentMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

@Service
public class TournamentKafkaProducer {

    private static final String TOPIC = "tournament.creation";
    private final KafkaTemplate<String, Object> kafkaTemplate;

    /**
     * used to convert the message to json
     */
    private final ObjectMapper objectMapper;


    @Autowired
    public TournamentKafkaProducer(KafkaTemplate<String, Object> kafkaTemplate, ObjectMapper objectMapper) {
        this.kafkaTemplate = kafkaTemplate;
        this.objectMapper = objectMapper;
    }

    public void sendTournamentCreationMessage(CreateTournamentMessage message) throws JsonProcessingException {
        String jsonMessage = objectMapper.writeValueAsString(message);
        kafkaTemplate.send(TOPIC, jsonMessage);
    }
}