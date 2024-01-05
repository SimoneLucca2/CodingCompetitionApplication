package com.polimi.ckb.user.service.kafkaConsumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.polimi.ckb.user.dto.StudentQuitsTournamentDto;
import com.polimi.ckb.user.service.TournamentService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.validation.Valid;


@Service
@RequiredArgsConstructor
public class StudentQuitsTournamentKafkaConsumer {

    private final ObjectMapper objectMapper;
    private final TournamentService tournamentService;

    @KafkaListener(topics = "tournament.student.quit", groupId = "user-service")
    public void listener(ConsumerRecord<String, String> record) {
        try {
            String message = record.value();
            @Valid StudentQuitsTournamentDto parsedMessage = objectMapper.readValue(message, StudentQuitsTournamentDto.class);
            processMessage(parsedMessage);
        } catch (Exception e) {
            System.err.println("Error processing message: " + e.getMessage());
        }
    }

    private void processMessage(StudentQuitsTournamentDto msg) {
        //add a new participant to the tournament
        tournamentService.studentQuitsTournament(msg);
    }
}
