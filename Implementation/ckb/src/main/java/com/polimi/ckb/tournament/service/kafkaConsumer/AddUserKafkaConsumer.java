package com.polimi.ckb.tournament.service.kafkaConsumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.polimi.ckb.tournament.dto.NewUserDto;
import com.polimi.ckb.tournament.service.EducatorService;
import com.polimi.ckb.tournament.service.StudentService;
import com.polimi.ckb.tournament.service.UserService;
import com.polimi.ckb.user.utility.UserType;
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
    private final Map<UserType, UserService> userServicesMap;

    @Autowired
    public AddUserKafkaConsumer(StudentService studentService, EducatorService educatorService) {
        this.objectMapper = new ObjectMapper();
        this.userServicesMap = new HashMap<>();
        userServicesMap.put(UserType.STUDENT, studentService);
        userServicesMap.put(UserType.EDUCATOR, educatorService);
    }

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
        UserService userService = userServicesMap.get(userDto.getType());
        if (userService == null) {
            throw new IllegalArgumentException("Unsupported user type: " + userDto.getType());
        }
        userService.addNewUser(userDto);
    }
}
