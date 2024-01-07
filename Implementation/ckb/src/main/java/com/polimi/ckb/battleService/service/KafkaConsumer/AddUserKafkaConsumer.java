package com.polimi.ckb.battleService.service.KafkaConsumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.polimi.ckb.battleService.dto.NewUserDto;
import com.polimi.ckb.battleService.service.EducatorService;
import com.polimi.ckb.battleService.service.StudentService;
import com.polimi.ckb.battleService.service.UserService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import com.polimi.ckb.user.utility.UserType;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;

@Service
@RequiredArgsConstructor
@Slf4j
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

    @KafkaListener(topics = "user.creation", groupId = "battle-service")
    public void listener(ConsumerRecord<String, String> record){
        try {
            String message = record.value();
            NewUserDto parsedMessage = objectMapper.readValue(message, NewUserDto.class);
            processMessage(parsedMessage);
        } catch (Exception e) {
            log.error("Error processing JSON: {}", e.getMessage());
            throw new RuntimeException(e);
        }
    }

    private void processMessage(NewUserDto userDto){
        UserService userService = userServicesMap.get(userDto.getType());
        if(userService == null){
            throw new IllegalArgumentException("Unsupported user type: " + userDto.getType());
        }
        userService.addNewUser(userDto);
    }
}
