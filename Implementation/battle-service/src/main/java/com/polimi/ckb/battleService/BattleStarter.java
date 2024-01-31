package com.polimi.ckb.battleService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
public class BattleStarter {
    public static void main(String[] args) {
        SpringApplication.run(BattleStarter.class, args);
    }
}
