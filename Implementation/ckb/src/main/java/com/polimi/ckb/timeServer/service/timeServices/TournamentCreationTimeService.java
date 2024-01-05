package com.polimi.ckb.timeServer.service.timeServices;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;

@Service
public class TournamentCreationTimeService {

    private final ScheduledExecutorService scheduler = Executors.newScheduledThreadPool(80);
    private final KafkaTemplate<String, String> kafkaTemplate;

    @Autowired
    public TournamentCreationTimeService(KafkaTemplate<String, String> kafkaTemplate) {
        this.kafkaTemplate = kafkaTemplate;
    }

    public void setTimer(long milliseconds, Long tournamentId) {
        scheduler.schedule(() -> {
            sendTournamentActiveMessage(tournamentId); //TODO cant autowire from kafka producer
        }, milliseconds, TimeUnit.MILLISECONDS);
    }
}