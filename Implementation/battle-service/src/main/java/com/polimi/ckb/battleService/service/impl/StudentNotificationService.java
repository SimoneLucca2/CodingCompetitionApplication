package com.polimi.ckb.battleService.service.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.polimi.ckb.battleService.dto.StudentInvitesToGroupDto;
import com.polimi.ckb.battleService.entity.Battle;
import com.polimi.ckb.battleService.service.kafkaProducer.NewBattleEmailKafkaProducer;
import com.polimi.ckb.battleService.service.kafkaProducer.StudentInvitationEmailKafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;


@Service
@RequiredArgsConstructor
public class StudentNotificationService {

    private final NewBattleEmailKafkaProducer newBattleEmailKafkaProducer;
    private final StudentInvitationEmailKafkaProducer studentInvitationEmailKafkaProducer;

    @Async
    public void sendToRegisteredStudents(Battle newBattle) {
        //send email to all students registered to the tournament
        newBattle.getStudentGroups().forEach(
                gr -> gr.getStudents().forEach(
                        st -> newBattleEmailKafkaProducer.sendBattleCreationEmail(
                                st.getStudentId(), newBattle
                        )
                )
        );
    }

    @Async @Retryable
    public void sendInvitationToStudent(StudentInvitesToGroupDto studentInvitesToGroupDto) throws UnsupportedEncodingException, JsonProcessingException {
        studentInvitationEmailKafkaProducer.sendEmailSendingRequestMessage(studentInvitesToGroupDto);
    }
}
