package com.polimi.ckb.battleService.service.KafkaConsumer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.polimi.ckb.battleService.dto.ChangeBattleStatusDto;
import com.polimi.ckb.battleService.entity.Battle;
import com.polimi.ckb.battleService.service.BattleService;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@Slf4j
public class StartBattleKafkaConsumer {
    private final ObjectMapper objectMapper;
    private final BattleService battleService;

    @Autowired
    public StartBattleKafkaConsumer(BattleService battleService) {
        this.objectMapper = new ObjectMapper();
        this.battleService = battleService;
    }

    @KafkaListener(topics = "battle.lifecycle.change", groupId = "battle-service")
    public void listener(ConsumerRecord<String, String> record) {
        try {
            String message = record.value();
            ChangeBattleStatusDto parsedMessage = objectMapper.readValue(message, ChangeBattleStatusDto.class);
            processMessage(parsedMessage);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private void processMessage(ChangeBattleStatusDto changeBattleStatusDto) {
        Battle battle = battleService.changeBattleStatus(changeBattleStatusDto);
        log.info("Battle \" " + battle.getBattleId() + "\" status successfully changed to: " + battle.getStatus());
    }
}
