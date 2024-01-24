package com.polimi.ckb.tournament.service.kafkaConsumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.polimi.ckb.tournament.config.TournamentStatus;
import com.polimi.ckb.tournament.dto.InternalChangeTournamentStatusDto;
import com.polimi.ckb.tournament.service.TournamentService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class ActivateTournamentKafkaConsumer {

    private final ObjectMapper objectMapper;
    private final TournamentService tournamentService;

    /**
     * Kafka listener method that listens for messages on the topic "tournament.lifecycle.active" with the group id "tournament-service".
     * The time service will send a message on this topic when the timeline expires.
     * @param record The {@link ConsumerRecord} object that contains the message.
     * @throws Exception if an error occurs during the processing of the message.
     */
    @KafkaListener(topics = "tournament.lifecycle.active", groupId = "tournament-service")
    public void listener(ConsumerRecord<String, String> record) {
        try {
            String message = record.value();
            InternalChangeTournamentStatusDto parsedMessage = objectMapper.readValue(message, InternalChangeTournamentStatusDto.class);
            processMessage(parsedMessage);
        } catch (Exception e) {
            System.err.println("Error processing message: " + e.getMessage());
        }
    }

    private void processMessage(InternalChangeTournamentStatusDto userDto) {
        tournamentService.updateTournamentStatus(userDto.getTournamentId(), TournamentStatus.ACTIVE);
    }

}
