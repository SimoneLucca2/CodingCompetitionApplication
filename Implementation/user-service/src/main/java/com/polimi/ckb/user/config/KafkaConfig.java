package com.polimi.ckb.user.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {
    @Bean
    public NewTopic tournamentCreationTopic() {
        return TopicBuilder.name("tournament.creation")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic emailTopic() {
        return TopicBuilder.name("email.sending.request")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic userCreationTopic() {
        return TopicBuilder.name("user.creation")
                .partitions(1)
                .replicas(1)
                .build();
    }

}
