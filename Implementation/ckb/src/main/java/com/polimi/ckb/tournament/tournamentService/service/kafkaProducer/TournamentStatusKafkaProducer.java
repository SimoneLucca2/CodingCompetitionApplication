package com.polimi.ckb.tournament.tournamentService.service.kafkaProducer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.polimi.ckb.tournament.tournamentService.config.TournamentStatus;
import com.polimi.ckb.tournament.tournamentService.dto.ChangeTournamentStatusDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
@RequiredArgsConstructor
public class TournamentStatusKafkaProducer {

    private static final Map<TournamentStatus, String> TOPIC_MAP = Map.of(
            TournamentStatus.ACTIVE, "tournament.lifecycle.active",
            TournamentStatus.CLOSING, "tournament.lifecycle.closing",
            TournamentStatus.CLOSED, "tournament.lifecycle.closed"
    );

    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Retryable(maxAttempts = 5, backoff = @Backoff(delay = 1000, multiplier = 1.5))
    public void sendTournamentMessage(ChangeTournamentStatusDto msg) throws JsonProcessingException {
        String TOPIC = TOPIC_MAP.getOrDefault(msg.getStatus(), "");

        if(TOPIC.isEmpty()) {
            return;
        }

        String jsonMessage = objectMapper.writeValueAsString(msg);
        kafkaTemplate.send(TOPIC, jsonMessage);
    }
}
