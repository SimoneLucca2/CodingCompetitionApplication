package com.polimi.ckb.tournament.service.kafkaConsumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.polimi.ckb.tournament.dto.NewUserDto;
import com.polimi.ckb.tournament.service.EducatorService;
import com.polimi.ckb.tournament.service.StudentService;
import com.polimi.ckb.tournament.service.UserService;
import com.polimi.ckb.tournament.service.userCreationStrategy.UserCreationStrategy;
import com.polimi.ckb.tournament.utility.UserType;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class AddUserKafkaConsumer {

    private final ObjectMapper objectMapper;
    private Map<UserType, UserCreationStrategy> userCreationStrategies;

    @Autowired
    public void setUserCreationStrategies(List<UserCreationStrategy> strategies){
        userCreationStrategies = strategies.stream()
                .collect(Collectors.toMap(UserCreationStrategy::getUserType, Function.identity()));
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
        UserCreationStrategy userCreationStrategy = userCreationStrategies.get(userDto.getType());
        if (userCreationStrategy == null) {
            throw new IllegalArgumentException("Unsupported user type: " + userDto.getType());
        }
        userCreationStrategy.addNewUser(userDto);
    }
}
