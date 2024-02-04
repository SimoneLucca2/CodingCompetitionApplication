package com.polimi.ckb.tournament.service.Impl;

import com.polimi.ckb.tournament.entity.Tournament;
import com.polimi.ckb.tournament.repository.StudentRepository;
import com.polimi.ckb.tournament.service.kafkaProducer.TournamentCreationEmailRequestKafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;


@Service
@RequiredArgsConstructor
public class StudentNotificationService {

    private final TournamentCreationEmailRequestKafkaProducer kafkaProducer;
    private final StudentRepository studentRepository;

    @Async
    public void sendToAllStudents(Tournament newTournament) {
        studentRepository.findAll().forEach(student -> {
            try {
                kafkaProducer.sendTournamentCreationEmailRequest(student.getStudentId(), newTournament);
            } catch (Exception e) {
                System.out.println("Error sending email to student " + student.getStudentId());
            }
        });
    }
}
