package com.polimi.ckb.timeServer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableScheduling //used to schedule polling tasks
public class TimeServerStarter {
    public static void main(String[] args) {
        SpringApplication.run(TimeServerStarter.class, args);
    }
}
