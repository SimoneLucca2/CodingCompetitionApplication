package com.polimi.ckb.tournament.tournamentService.service.kafkaConsumer;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.polimi.ckb.tournament.tournamentService.dto.UpdateStudentScoreInTournamentDto;
import com.polimi.ckb.tournament.tournamentService.service.TournamentService;
import lombok.AllArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class UpdateTournamentScoreKafkaConsumer {

    private final ObjectMapper objectMapper;
    private final TournamentService tournamentService;

    @KafkaListener(topics = "battle.student.score", groupId = "tournament-service")
    public void listener(String message) throws JsonProcessingException {
        UpdateStudentScoreInTournamentDto msg = objectMapper.readValue(message, UpdateStudentScoreInTournamentDto.class);
        tournamentService.updateTournamentScore(msg);
    }
}
