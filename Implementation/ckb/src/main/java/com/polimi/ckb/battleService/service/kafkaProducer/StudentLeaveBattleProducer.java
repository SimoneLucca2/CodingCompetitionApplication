package com.polimi.ckb.battleService.service.kafkaProducer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.polimi.ckb.battleService.dto.StudentLeaveBattleDto;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class StudentLeaveBattleProducer {
    private static final String TOPIC = "battle.student.quit";
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Retryable(maxAttempts = 5, backoff = @Backoff(delay = 1000, multiplier = 1.5))
    public void sendStudentLeavesBattleMessage(StudentLeaveBattleDto studentLeaveBattleDto) throws JsonProcessingException {
        String jsonMessage = objectMapper.writeValueAsString(studentLeaveBattleDto);
        kafkaTemplate.send(TOPIC, jsonMessage);
    }
}
