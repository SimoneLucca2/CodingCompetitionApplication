package com.polimi.ckb.user.service.kafkaConsumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.polimi.ckb.user.dto.EducatorJoinTournamentDto;
import com.polimi.ckb.user.dto.StudentJoinTournamentDto;
import com.polimi.ckb.user.entity.Educator;
import com.polimi.ckb.user.service.TournamentService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.validation.Valid;


@Service
@RequiredArgsConstructor
public class EducatorJoinTournamentKafkaConsumer {

    private final ObjectMapper objectMapper;
    private final TournamentService tournamentService;

    @KafkaListener(topics = "tournament.educator.add", groupId = "user-service")
    public void listener(ConsumerRecord<String, String> record) {
        try {
            String message = record.value();
            @Valid EducatorJoinTournamentDto parsedMessage = objectMapper.readValue(message, EducatorJoinTournamentDto.class);
            processMessage(parsedMessage);
        } catch (Exception e) {
            System.err.println("Error processing message: " + e.getMessage());
        }
    }

    private void processMessage(EducatorJoinTournamentDto msg) {
        //add a new educator to the tournament
        tournamentService.addEducatorToTournament(msg);
    }
}
