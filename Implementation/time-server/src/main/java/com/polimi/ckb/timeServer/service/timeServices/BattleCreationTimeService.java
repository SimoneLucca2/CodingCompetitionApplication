package com.polimi.ckb.timeServer.service.timeServices;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.polimi.ckb.timeServer.dto.ChangeBattleStatusDto;
import com.polimi.ckb.timeServer.dto.CreatedBattleDto;
import com.polimi.ckb.timeServer.service.kafkaProducer.BattleChangeStatusKafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class BattleCreationTimeService {

    private final int THREAD_POOL_SIZE = 80;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(THREAD_POOL_SIZE);
    private final BattleChangeStatusKafkaProducer battleChangeStatusKafkaProducer;

    @Retryable
    public void setTimer(long milliseconds, CreatedBattleDto msg) {
        scheduler.schedule(() -> {
            try {
                battleChangeStatusKafkaProducer.sendBattleActiveMessage(
                        ChangeBattleStatusDto
                                .builder()
                                .battleId(msg.getBattleId())
                                .educatorId(msg.getCreatorId())
                                .status(msg.getStatus())
                                .build()
                );
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }, milliseconds, TimeUnit.MILLISECONDS);
    }

}