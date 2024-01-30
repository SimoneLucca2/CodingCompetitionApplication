package com.polimi.ckb.tournament.service.kafkaConsumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.polimi.ckb.tournament.dto.NewUserDto;
import com.polimi.ckb.tournament.service.kafkaConsumer.AddUserKafkaConsumer;
import com.polimi.ckb.tournament.service.userCreationStrategy.UserCreationStrategy;
import com.polimi.ckb.tournament.utility.UserType;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.boot.test.context.SpringBootTest;

import java.util.Collections;
import java.util.HashMap;
import java.util.Map;

@SpringBootTest
public class AddUserKafkaConsumerTest {

    @Mock
    private ObjectMapper objectMapper;
    @Mock
    private UserCreationStrategy userCreationStrategy;
    @InjectMocks
    private AddUserKafkaConsumer addUserKafkaConsumer;

    @Test
    public void testListener() throws Exception {
        // Given
        ConsumerRecord<String, String> record = new ConsumerRecord<>("user.creation", 1, 1L, "key", "value");
        NewUserDto userDto = new NewUserDto();
        userDto.setType(UserType.EDUCATOR);

        // When
        Mockito.when(objectMapper.readValue(record.value(), NewUserDto.class)).thenReturn(userDto);
        Map<UserType, UserCreationStrategy> strategies = new HashMap<>();
        strategies.put(UserType.EDUCATOR, userCreationStrategy);
        addUserKafkaConsumer.setUserCreationStrategies(Collections.singletonList(userCreationStrategy));

        // Then
        addUserKafkaConsumer.listener(record);
        Mockito.verify(userCreationStrategy).addNewUser(userDto);
    }

    @Test
    public void testListenerException() {
        // Given
        ConsumerRecord<String, String> record = new ConsumerRecord<>("user.creation", 1, 1L, "key", "value");
        addUserKafkaConsumer.setUserCreationStrategies(Collections.singletonList(userCreationStrategy));

        try {
            // When
            addUserKafkaConsumer.listener(record);
        } catch (Exception e) {
            // Then
            // Expected IllegalArgumentException as no strategy is set
            assert(e instanceof IllegalArgumentException);
        }
    }
}