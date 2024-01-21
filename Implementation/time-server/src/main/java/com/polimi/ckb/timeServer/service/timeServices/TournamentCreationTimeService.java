package com.polimi.ckb.timeServer.service.timeServices;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.polimi.ckb.timeServer.dto.CreatedTournamentKafkaDto;
import com.polimi.ckb.timeServer.service.kafkaProducer.TournamentActiveKafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
@RequiredArgsConstructor
public class TournamentCreationTimeService {
    private final int THREAD_POOL_SIZE = 80;

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(THREAD_POOL_SIZE);
    private final TournamentActiveKafkaProducer tournamentActiveKafkaProducer;

    @Retryable
    public void setTimer(long milliseconds, CreatedTournamentKafkaDto msg) {
        scheduler.schedule(() -> {
            try {
                tournamentActiveKafkaProducer.sendTournamentActiveMessage(msg);
            } catch (JsonProcessingException e) {
                throw new RuntimeException(e);
            }
        }, milliseconds, TimeUnit.MILLISECONDS);
    }
}