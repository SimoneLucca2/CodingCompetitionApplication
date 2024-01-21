package com.polimi.ckb.tournament.service.kafkaConsumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.polimi.ckb.tournament.dto.NewUserDto;
import com.polimi.ckb.tournament.service.EducatorService;
import com.polimi.ckb.tournament.service.StudentService;
import com.polimi.ckb.tournament.service.UserService;
import com.polimi.ckb.tournament.utility.UserType;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class AddUserKafkaConsumer {

    private final ObjectMapper objectMapper;
    private final StudentService studentService;
    private final EducatorService educatorService;

    @KafkaListener(topics = "user.creation", groupId = "tournament-service")
    public void listener(ConsumerRecord<String, String> record) {
        try {
            String message = record.value();
            NewUserDto parsedMessage = objectMapper.readValue(message, NewUserDto.class);
            processMessage(parsedMessage);
        } catch (Exception e) {
            System.err.println("Error processing message: " + e.getMessage());
        }
    }

    private void processMessage(NewUserDto userDto) {
        switch (userDto.getType()) {
            case STUDENT:
                studentService.addNewUser(userDto);
                break;
            case EDUCATOR:
                educatorService.addNewUser(userDto);
                break;
            default:
                throw new IllegalArgumentException("Unsupported user type: " + userDto.getType());
        }
    }
}
