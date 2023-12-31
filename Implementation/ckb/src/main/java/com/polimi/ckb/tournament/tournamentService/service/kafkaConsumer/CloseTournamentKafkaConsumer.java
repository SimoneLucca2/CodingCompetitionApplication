package com.polimi.ckb.tournament.tournamentService.service.kafkaConsumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.polimi.ckb.tournament.tournamentService.dto.ChangeTournamentStatusDto;
import com.polimi.ckb.tournament.tournamentService.dto.InternalChangeTournamentStatusDto;
import com.polimi.ckb.tournament.tournamentService.service.TournamentStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@RequiredArgsConstructor
@Service
public class CloseTournamentKafkaConsumer {

    private TournamentStatusService tournamentStatus;
    private final ObjectMapper objectMapper;

    @KafkaListener(topics = "tournament.lifecycle.close", groupId = "tournament-service")
    public void listener(String message) throws JsonProcessingException {
        InternalChangeTournamentStatusDto parsedMessage = objectMapper.readValue(message, InternalChangeTournamentStatusDto.class);
        tournamentStatus.updateTournamentStatus(ChangeTournamentStatusDto.builder().status(parsedMessage.getStatus()).build());

        System.out.println("Received message: " + message);
    } //TODO check this
}
