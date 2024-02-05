package com.polimi.ckb.battleService.service.kafkaProducer;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.polimi.ckb.battleService.dto.EmailDto;
import com.polimi.ckb.battleService.dto.StudentInvitesToGroupDto;
import com.polimi.ckb.battleService.dto.TournamentDto;
import com.polimi.ckb.battleService.dto.UsernameDto;
import com.polimi.ckb.battleService.utility.informationGetter.EmailGetter;
import com.polimi.ckb.battleService.utility.informationGetter.UsernameGetter;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;

@Service
@AllArgsConstructor
public class StudentInvitationEmailKafkaProducer {
    private static final String TOPIC = "email.sending.request";
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final UsernameGetter usernameGetter;
    private final EmailGetter emailGetter;
    private final ObjectMapper objectMapper;

    @Retryable(maxAttempts = 5, backoff = @Backoff(delay = 1000, multiplier = 1.5))
    @Async
    public void sendEmailSendingRequestMessage(StudentInvitesToGroupDto studentInvitesToGroupDto) throws JsonProcessingException, UnsupportedEncodingException {

        String receiver = usernameGetter.getUsername(studentInvitesToGroupDto.getStudentId());
        String receiverMail = emailGetter.getEmail(studentInvitesToGroupDto.getStudentId());

        EmailDto emailDto = EmailDto.builder()
                .to(receiverMail)
                .from("simone.lucca.00@gmail.com")
                .cc(new String[]{})
                .bcc(new String[]{})
                .subject("You've Been Invited to Join a Group by Your Peer!")
                .body("Dear " + receiver + ",\\n\\nYour fellow student, a peer has invited you to join their group for the upcoming battle. This is an excellent opportunity for you to collaborate, learn, and showcase your skills in a supportive and competitive environment.\\n\\nTo accept this invitation, please enter the following code into the join group section:\\n " + studentInvitesToGroupDto.getGroupId() + ".\\n\\nIf you have any questions or need further information, feel free to reach out to us or [Inviting Student's Name] directly.\\n\\nWe hope to see you in the group soon!\\n\\nBest regards,\\nCKB Team")
                .build();

        String jsonMessage = objectMapper.writeValueAsString(emailDto);
        kafkaTemplate.send(TOPIC, jsonMessage);
    }
}
