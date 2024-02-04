package com.polimi.ckb.battleService.service.KafkaConsumer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.polimi.ckb.battleService.config.TournamentStatus;
import com.polimi.ckb.battleService.dto.ChangeTournamentStatusDto;
import com.polimi.ckb.battleService.dto.InternalChangeTournamentStatusDto;
import com.polimi.ckb.battleService.service.BattleService;
import com.polimi.ckb.battleService.service.kafkaProducer.CloseTournamentKafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
@RequiredArgsConstructor
public class ChangeTournamentStatusKafkaConsumer {
    private final ObjectMapper objectMapper;
    private final BattleService battleService;
    private final CloseTournamentKafkaProducer closeTournamentKafkaProducer;

    @KafkaListener(topics = "battle.lifecycle.closing", groupId = "battle-service")
    public void listener(ConsumerRecord<String, String> record) {
        try {
            String message = record.value();
            ChangeTournamentStatusDto parsedMessage = objectMapper.readValue(message, ChangeTournamentStatusDto.class);
            processMessage(parsedMessage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void processMessage(ChangeTournamentStatusDto changeBattleStatusDto) throws JsonProcessingException {
        battleService.closeAllBattleInTournament(changeBattleStatusDto.getTournamentId());
        log.info("Battles closed");
        closeTournamentKafkaProducer.closeTournament(
                InternalChangeTournamentStatusDto.builder()
                        .tournamentId(changeBattleStatusDto.getTournamentId())
                        .status(TournamentStatus.CLOSED)
                        .build()
        );
    }
}
