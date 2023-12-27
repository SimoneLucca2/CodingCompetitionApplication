package com.polimi.ckb.tournament.tournamentService;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.autoconfigure.jdbc.DataSourceAutoConfiguration;

@SpringBootApplication(exclude = {DataSourceAutoConfiguration.class})
public class TournamentStarter {
    public static void main(String[] args) {
        SpringApplication.run(TournamentStarter.class, args);
    }
}
