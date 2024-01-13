package com.polimi.ckb.battleService.service.kafkaProducer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.polimi.ckb.battleService.dto.StudentInvitesToGroupDto;
import com.polimi.ckb.battleService.dto.StudentJoinBattleDto;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class EmailSendingRequestKafkaProducer {
    private static final String TOPIC = "email.send.request";
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;

    @Retryable(maxAttempts = 5, backoff = @Backoff(delay = 1000, multiplier = 1.5))
    public void sendEmailSendingRequestMessage(StudentInvitesToGroupDto studentInvitesToGroupDto) throws JsonProcessingException {
        String jsonMessage = objectMapper.writeValueAsString(studentInvitesToGroupDto);
        kafkaTemplate.send(TOPIC, jsonMessage);
    }
}
