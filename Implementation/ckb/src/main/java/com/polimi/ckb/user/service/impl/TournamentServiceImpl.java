package com.polimi.ckb.user.service.impl;

import com.polimi.ckb.user.dto.StudentJoinTournamentDto;
import com.polimi.ckb.user.entity.Student;
import com.polimi.ckb.user.entity.Tournament;
import com.polimi.ckb.user.repository.StudentRepository;
import com.polimi.ckb.user.repository.TournamentRepository;
import com.polimi.ckb.user.service.TournamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
public class TournamentServiceImpl implements TournamentService {

    private final TournamentRepository tournamentRepository;
    private final StudentRepository studentRepository;

    @Override
    public Tournament saveTournament(Tournament tournament) {
        return tournamentRepository.save(tournament);
    }

    @Override
    public Tournament studentJoinTournament(@Valid StudentJoinTournamentDto msg) {

        Student student = studentRepository.findById(msg.getStudentId()).orElseThrow(() -> new RuntimeException("Student not found"));
        Tournament tournament = tournamentRepository.findById(msg.getTournamentId()).orElseThrow(() -> new RuntimeException("Tournament not found"));

        tournament.getStudents().add(student);
        tournamentRepository.save(tournament);

        return tournament;
    }
}
