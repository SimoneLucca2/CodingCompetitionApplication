package com.polimi.ckb.authentication.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    @Bean
    public NewTopic authenticationCreationTopic() {
        return TopicBuilder.name("authentication.creation")
                .partitions(1)
                .replicas(1)
                .build();
    }
}
