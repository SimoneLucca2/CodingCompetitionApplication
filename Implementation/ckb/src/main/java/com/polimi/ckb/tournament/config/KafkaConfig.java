package com.polimi.ckb.tournament.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.config.TopicBuilder;

@Configuration
public class KafkaConfig {

    @Bean
    public NewTopic battleStudentScoreTopic() {
        return TopicBuilder.name("battle.student.score")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic emailSendingRequestTopic() {
        return TopicBuilder.name("email.sending.request")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic inputTopicTopic() {
        return TopicBuilder.name("inputTopic")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic tournamentCreationTopic() {
        return TopicBuilder.name("tournament.creation")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic tournamentEducatorAddTopic() {
        return TopicBuilder.name("tournament.educator.add")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic tournamentLifecycleActiveTopic() {
        return TopicBuilder.name("tournament.lifecycle.active")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic tournamentLifecycleCloseTopic() {
        return TopicBuilder.name("tournament.lifecycle.close")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic tournamentLifecycleClosingTopic() {
        return TopicBuilder.name("tournament.lifecycle.closing")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic tournamentStudentJoinTopic() {
        return TopicBuilder.name("tournament.student.join")
                .partitions(1)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic tournamentStudentQuitTopic() {
        return TopicBuilder.name("tournament.student.quit")
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
