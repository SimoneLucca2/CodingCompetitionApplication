package com.polimi.ckb.tournament.tournamentService.service.kafkaProducer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.polimi.ckb.tournament.tournamentService.dto.StudentJoinTournamentDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class StudentJoinTournamentKafkaProducer {
    private static final String TOPIC = "tournament.student.join";
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Retryable(maxAttempts = 5, backoff = @Backoff(delay = 1000, multiplier = 1.5))
    public void sendStudentJoinMessage(StudentJoinTournamentDto msg) throws JsonProcessingException {
        String jsonMessage = objectMapper.writeValueAsString(msg);
        kafkaTemplate.send(TOPIC, jsonMessage);
    }
}
