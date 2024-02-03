package com.polimi.ckb.battleService.service.kafkaProducer;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.polimi.ckb.battleService.dto.EmailDto;
import com.polimi.ckb.battleService.entity.Battle;
import com.polimi.ckb.battleService.utility.informationGetter.EmailGetter;
import com.polimi.ckb.battleService.utility.informationGetter.UsernameGetter;
import lombok.AllArgsConstructor;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
@AllArgsConstructor
public class NewBattleEmailKafkaProducer {
    private static final String TOPIC = "email.sending.request";
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final ObjectMapper objectMapper;
    private final EmailGetter emailGetter;
    private final UsernameGetter usernameGetter;

    @Retryable(maxAttempts = 5, backoff = @Backoff(delay = 1000, multiplier = 1.5))
    @Async
    public void sendBattleCreationEmail(Long studentId, Battle battle) {

        try{

            String receiverMail = emailGetter.getEmail(studentId);
            String receiverName = usernameGetter.getUsername(studentId);

            EmailDto emailDto = EmailDto.builder()
                    .to(receiverMail)
                    .from("CKB Team")
                    .cc(new String[]{})
                    .bcc(new String[]{})
                    .subject("Battle \"Battle of the Code\" Has Been Created!")
                    .body("Dear " + receiverName + ",\\n\\nWe are excited to inform you that the battle " +
                            "\\\"" + battle.getName() + "\\\" has now begun!\\n\\nThis is your chance to showcase your" +
                            " skills and compete for the top spot. Make sure to review the guidelines and submit your " +
                            "projects before the deadline.\\n\\nGood luck, and may the best team win!\\n\\n" +
                            "Best regards,\\nCKB Team\"\n")
                    .build();

            String jsonMessage = objectMapper.writeValueAsString(emailDto);
            kafkaTemplate.send(TOPIC, jsonMessage);

        }catch (IOException ignored){}
    }
}
