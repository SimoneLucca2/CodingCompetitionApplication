package com.polimi.ckb.tournament.service.kafkaProducer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.polimi.ckb.tournament.dto.EmailDto;
import com.polimi.ckb.tournament.entity.Tournament;
import com.polimi.ckb.tournament.utility.informationGetter.EmailGetter;
import com.polimi.ckb.tournament.utility.informationGetter.UsernameGetter;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class TournamentCreationEmailRequestKafkaProducer {

    private static final String TOPIC = "email.sending.request";
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final EmailGetter emailGetter;
    private final UsernameGetter usernameGetter;


    @Retryable(maxAttempts = 5, backoff = @Backoff(delay = 1000, multiplier = 1.5))
    @Async
    public void sendTournamentCreationEmailRequest(Long studentId, Tournament newTournament) throws JsonProcessingException {

        String receiverMail = emailGetter.getEmail(studentId);
        String receiverName = usernameGetter.getUsername(studentId);

        String body = """
                Dear %s:,

                We are excited to announce the launch of a new tournament: %s. This is an excellent opportunity for you to showcase your skills, learn, and compete with your peers.

                Please note the following important dates:
                - **Registration Deadline**: %s
                
                Make sure to register before the deadline to secure your participation. We are looking forward to seeing your innovative solutions and wish you the best of luck in the competition.

                Best regards,
                CKB Team""";

        EmailDto emailDto = EmailDto.builder()
                .to(receiverMail)
                .from("CKB Team")
                .cc(new String[]{})
                .bcc(new String[]{})
                .subject("Battle \"Battle of the Code\" Has Been Created!")
                .body(String.format(body, receiverName, newTournament.getName(), newTournament.getRegistrationDeadline()))
                .build();

        String jsonMessage = objectMapper.writeValueAsString(emailDto);
        kafkaTemplate.send(TOPIC, jsonMessage);
    }
}

