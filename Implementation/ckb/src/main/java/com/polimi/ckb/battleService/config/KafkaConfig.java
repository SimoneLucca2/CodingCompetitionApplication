package com.polimi.ckb.battleService.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    @Bean
    public NewTopic battleCreationTopic(){
        return TopicBuilder.name("battle.creation")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic battleJoinTopic(){
        return TopicBuilder.name("battle.student.join")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic battleLeaveTopic(){
        return TopicBuilder.name("battle.student.leave")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
