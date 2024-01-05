package com.polimi.ckb.timeServer.service.kafkaConsumer;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.polimi.ckb.timeServer.dto.CreatedTournamentKafkaDto;
import com.polimi.ckb.timeServer.service.timeServices.TournamentCreationTimeService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.polimi.ckb.timeServer.utility.TimeTypeConverter.calculateMillisecondsToDeadline;

@AllArgsConstructor
@Service
public class TournamentCreationKafkaConsumer {

    private final ObjectMapper objectMapper;
    private final TournamentCreationTimeService tournamentCreationTimeService;

    @KafkaListener(topics = "tournament.creation", groupId = "time-server")
    public void listener(String message) throws JsonProcessingException {
        CreatedTournamentKafkaDto msg = objectMapper.readValue(message, CreatedTournamentKafkaDto.class);

        tournamentCreationTimeService.setTimer(calculateMillisecondsToDeadline(msg.getRegistrationDeadline()), msg.getTournamentId());
    }

}
