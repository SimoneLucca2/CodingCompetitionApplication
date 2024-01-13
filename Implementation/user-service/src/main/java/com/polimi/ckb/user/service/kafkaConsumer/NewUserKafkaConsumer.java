package com.polimi.ckb.user.service.kafkaConsumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.polimi.ckb.user.dto.NewUserDto;
import com.polimi.ckb.user.service.EducatorService;
import com.polimi.ckb.user.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import javax.validation.Valid;


@Service
@RequiredArgsConstructor
public class NewUserKafkaConsumer {

    private final ObjectMapper objectMapper;
    private final StudentService studentService;
    private final EducatorService educatorService;

    @KafkaListener(topics = "user.creation", groupId = "user-service")
    public void listener(ConsumerRecord<String, String> record) {
        try {
            String message = record.value();
            @Valid NewUserDto parsedMessage = objectMapper.readValue(message, NewUserDto.class);
            processMessage(parsedMessage);
        } catch (Exception e) {
            System.err.println("Error processing message: " + e.getMessage());
        }
    }

    private void processMessage(NewUserDto msg) {
        //add a new participant to the tournament
        switch (msg.getType()) {
            case STUDENT:
                studentService.addNewStudent(msg);
                break;
            case EDUCATOR:
                educatorService.addNewEducator(msg);
                break;
            default:
                throw new IllegalArgumentException("Unsupported user type: " + msg.getType());
        }

    }
}
