package com.polimi.ckb.battleService.service.impl;


import com.fasterxml.jackson.core.JsonProcessingException;
import com.polimi.ckb.battleService.dto.StudentInvitesToGroupDto;
import com.polimi.ckb.battleService.entity.Battle;
import com.polimi.ckb.battleService.service.kafkaProducer.NewBattleEmailKafkaProducer;
import com.polimi.ckb.battleService.service.kafkaProducer.StudentInvitationEmailKafkaProducer;
import com.polimi.ckb.battleService.utility.informationGetter.EmailGetter;
import com.polimi.ckb.battleService.utility.informationGetter.TournamentParticipantsGetter;
import lombok.RequiredArgsConstructor;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.util.List;


@Service
@RequiredArgsConstructor
public class StudentNotificationService {

    private final NewBattleEmailKafkaProducer newBattleEmailKafkaProducer;
    private final StudentInvitationEmailKafkaProducer studentInvitationEmailKafkaProducer;
    private final TournamentParticipantsGetter tournamentParticipantsGetter;

    @Async
    public void sendToRegisteredStudents(Battle newBattle) {
        //send email to all students registered to the tournament
        Long tournamentId = newBattle.getTournamentId();
        List<Long> registeredStudents = tournamentParticipantsGetter.getParticipants(tournamentId);
        registeredStudents.forEach(sId -> newBattleEmailKafkaProducer.sendBattleCreationEmail(sId, newBattle));
    }

    @Async @Retryable
    public void sendInvitationToStudent(StudentInvitesToGroupDto studentInvitesToGroupDto) throws UnsupportedEncodingException, JsonProcessingException {
        studentInvitationEmailKafkaProducer.sendEmailSendingRequestMessage(studentInvitesToGroupDto);
    }
}
