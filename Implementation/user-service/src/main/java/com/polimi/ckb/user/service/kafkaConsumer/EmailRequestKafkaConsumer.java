package com.polimi.ckb.user.service.kafkaConsumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.polimi.ckb.user.dto.EmailDto;
import com.polimi.ckb.user.repository.UserRepository;
import com.polimi.ckb.user.service.EmailService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.validation.Valid;


@Service
@RequiredArgsConstructor
public class EmailRequestKafkaConsumer {

    private final ObjectMapper objectMapper;
    private final EmailService emailService;
    private final UserRepository userRepository;

    @KafkaListener(topics = "email.sending.request", groupId = "email-service")
    public void listener(ConsumerRecord<String, String> record) {
        try {
            String message = record.value();
            @Valid EmailDto parsedMessage = objectMapper.readValue(message, EmailDto.class);
            emailService.sendEmail(parsedMessage);
        } catch (Exception e) {
            System.err.println("Error processing message: " + e.getMessage());
        }
    }
}
