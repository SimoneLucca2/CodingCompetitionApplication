package com.polimi.ckb.user.service.kafkaConsumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.polimi.ckb.user.dto.NewTournamentDto;
import com.polimi.ckb.user.entity.Educator;
import com.polimi.ckb.user.entity.Tournament;
import com.polimi.ckb.user.service.EducatorService;
import com.polimi.ckb.user.service.TournamentService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.polimi.ckb.user.utility.entityConverter.NewTournamentDtoToTournament.convertToEntity;

/**
 * This class is a Kafka consumer that listens to the "tournament.creation" topic and processes incoming messages.
 * The messages are expected to be in the form of a JSON string representing a NewTournamentDto object.
 * Upon receiving messages from the topic, saves the new tournament in the database.
 */
@Service
@RequiredArgsConstructor
public class NewTournamentKafkaConsumer {

    private final ObjectMapper objectMapper;
    private final TournamentService tournamentService;
    private final EducatorService educatorService;

    @KafkaListener(topics = "tournament.creation", groupId = "user-service")
    public void listener(ConsumerRecord<String, String> record) {
        try {
            String message = record.value();
            NewTournamentDto parsedMessage = objectMapper.readValue(message, NewTournamentDto.class);
            processMessage(parsedMessage);
        } catch (Exception e) {
            System.err.println("Error processing message: " + e.getMessage());
        }
    }

    private void processMessage(NewTournamentDto msg) {
        Tournament tournament = convertToEntity(msg);

        Educator educator = educatorService.getEducatorById(
                msg.getEducatorId()).orElseThrow(() -> new RuntimeException("Educator must exist")
        ); //educator must exist

        //save the creator of the tournament
        tournament.setCreator(educator);  // setting the creatorId to the educationalId from the DTO.
        tournamentService.saveTournament(tournament);
    }
}
