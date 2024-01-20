package com.polimi.ckb.user.service.kafkaConsumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.polimi.ckb.user.dto.CreateBattleDto;
import com.polimi.ckb.user.entity.Battle;
import com.polimi.ckb.user.entity.Educator;
import com.polimi.ckb.user.repository.BattleRepository;
import com.polimi.ckb.user.repository.EducatorRepository;
import com.polimi.ckb.user.service.EducatorService;
import com.polimi.ckb.user.service.TournamentService;
import com.polimi.ckb.user.utility.entityConverter.CreateBattleDtoToBattle;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

import static com.polimi.ckb.user.utility.entityConverter.NewTournamentDtoToTournament.convertToEntity;

/**
 * This class is a Kafka consumer that listens to the "tournament.creation" topic and processes incoming messages.
 * The messages are expected to be in the form of a JSON string representing a NewTournamentDto object.
 * Upon receiving messages from the topic, saves the new tournament in the database.
 */
@Service
@RequiredArgsConstructor
public class BattleCreationKafkaConsumer {

    private final ObjectMapper objectMapper;
    private final BattleRepository battleRepository;
    private final EducatorRepository educatorRepository;

    @KafkaListener(topics = "battle.creation", groupId = "user-service")
    public void listener(ConsumerRecord<String, String> record) {
        try {
            String message = record.value();
            CreateBattleDto parsedMessage = objectMapper.readValue(message, CreateBattleDto.class);
            processMessage(parsedMessage);
        } catch (Exception e) {
            System.err.println("Error processing message: " + e.getMessage());
        }
    }

    private void processMessage(CreateBattleDto msg) {
        Battle battle = CreateBattleDtoToBattle.toEntity(msg);

        Educator creator = educatorRepository.findById(msg.getCreatorId()).orElseThrow(() -> new RuntimeException("Educator must exist")); //educator must exist

        //save the creator of the tournament
        battle.setBattleCreator(creator);  // setting the creatorId to the educationalId from the DTO.
        battleRepository.save(battle);
    }
}
