package com.polimi.ckb.apiGateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;


@SpringBootApplication
@EnableDiscoveryClient
public class ApiGatewayStarter {

    public static void main(String[] args) {
        SpringApplication.run(ApiGatewayStarter.class, args);
    }
}
