package com.polimi.ckb.timeServer.service.timeServices;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.polimi.ckb.timeServer.config.BattleStatus;
import com.polimi.ckb.timeServer.dto.ChangeBattleStatusDto;
import com.polimi.ckb.timeServer.entity.Battle;
import com.polimi.ckb.timeServer.repository.BattleRepository;
import com.polimi.ckb.timeServer.service.kafka.BattleChangeStatusKafkaProducer;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class BattleRoutine {

    private final BattleRepository battleRepository;
    private final BattleChangeStatusKafkaProducer kafkaProducer;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    public BattleRoutine(BattleRepository battleRepository, BattleChangeStatusKafkaProducer kafkaProducer) {
        this.battleRepository = battleRepository;
        this.kafkaProducer = kafkaProducer;
    }


    /**
     * Checks the battle registration deadline and updates the battle status accordingly.
     * This method is scheduled to run at fixed intervals defined by the "battle.polling.rate" property.
     * It retrieves all battles with registration deadline passed and status set to "PRE_BATTLE".
     * For each battle, it sets the status to "BATTLE" and saves the updated battle in the repository.
     */
    @Scheduled(fixedRateString = "#{T(java.lang.Integer).valueOf('${battle.polling.rate}')}")
    @Async
    public void checkBattleRegistrationDeadline() {
        String currentTime = sdf.format(new Date());
        List<Battle> battles = battleRepository.findAllWithRegistrationDeadlinePassedAndStatusPreBattle(currentTime);

        battles.forEach(battle -> {
            battle.setStatus(BattleStatus.BATTLE);
            battleRepository.save(battle);

            try {
                kafkaProducer.sendBattleChangeMessage(
                        ChangeBattleStatusDto.builder()
                                .battleId(battle.getBattleId())
                                .status(BattleStatus.BATTLE)
                                .build()
                );
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }

    /**
     * Checks the battle submission deadline and updates the battle status accordingly.
     * This method is scheduled to run at fixed intervals defined by the "battle.polling.rate" property.
     * It retrieves all battles with submission deadline passed and status set to "BATTLE".
     * For each battle, it sets the status to "CONSOLIDATION" and saves the updated battle in the repository.
     */
    @Scheduled(fixedRateString = "#{T(java.lang.Integer).valueOf('${battle.polling.rate}')}")
    @Async
    public void checkBattleSubmissionDeadline() {
        String currentTime = sdf.format(new Date());
        List<Battle> battles = battleRepository.findAllWithSubmissionDeadlinePassedAndStatusBattle(currentTime);
        battles.forEach(battle -> {
            battle.setStatus(BattleStatus.CONSOLIDATION);
            battleRepository.save(battle);

            try {
                kafkaProducer.sendBattleChangeMessage(
                        ChangeBattleStatusDto.builder()
                                .battleId(battle.getBattleId())
                                .status(BattleStatus.CONSOLIDATION)
                                .build()
                );
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        });
    }
}

