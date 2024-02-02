package com.polimi.ckb.battleService.service.KafkaConsumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.polimi.ckb.battleService.dto.StudentQuitTournamentDto;
import com.polimi.ckb.battleService.service.BattleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StudentQuitTournamentConsumer {
    private final ObjectMapper objectMapper;
    private final BattleService battleService;

    @Autowired
    public StudentQuitTournamentConsumer(BattleService battleService){
        this.objectMapper = new ObjectMapper();
        this.battleService = battleService;
    }

    @KafkaListener(topics = "tournament.student.quit", groupId = "battle-service")
    public void listener(ConsumerRecord<String, String> record) {
        try {
            String message = record.value();
            StudentQuitTournamentDto parsedMessage = objectMapper.readValue(message, StudentQuitTournamentDto.class);
            processMessage(parsedMessage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void processMessage(StudentQuitTournamentDto studentQuitTournamentDto) {
        battleService.quitEntireTournament(studentQuitTournamentDto);
        log.info("Student " + studentQuitTournamentDto.getStudentId() + " has quit tournament " + studentQuitTournamentDto.getTournamentId() + " and all its battle");
    }
}
