package com.polimi.ckb.tournament.service.kafkaConsumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.polimi.ckb.tournament.dto.InternalChangeTournamentStatusDto;
import com.polimi.ckb.tournament.service.TournamentStatusService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class CloseTournamentKafkaConsumer {

    private TournamentStatusService tournamentStatus;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "tournament.lifecycle.close", groupId = "tournament-service")
    public void listener(String message) throws JsonProcessingException {
        InternalChangeTournamentStatusDto parsedMessage = objectMapper.readValue(message, InternalChangeTournamentStatusDto.class);
        tournamentStatus.updateTournamentStatus(parsedMessage.getTournamentId(), parsedMessage.getStatus());
    }
}
