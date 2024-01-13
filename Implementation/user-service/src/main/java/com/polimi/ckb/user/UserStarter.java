package com.polimi.ckb.user;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class UserStarter {
    public static void main(String[] args) {
        SpringApplication.run(com.polimi.ckb.user.UserStarter.class, args);
    }

}
