package com.polimi.ckb.tournament.tournamentService.service.Impl;

import com.polimi.ckb.tournament.tournamentService.config.TournamentStatus;
import com.polimi.ckb.tournament.tournamentService.dto.CreateTournamentDto;
import com.polimi.ckb.tournament.tournamentService.dto.UpdateStudentScoreInTournamentDto;
import com.polimi.ckb.tournament.tournamentService.entity.Score;
import com.polimi.ckb.tournament.tournamentService.entity.Student;
import com.polimi.ckb.tournament.tournamentService.entity.Tournament;
import com.polimi.ckb.tournament.tournamentService.exception.TournamentAlreadyExistException;
import com.polimi.ckb.tournament.tournamentService.repository.ScoreRepository;
import com.polimi.ckb.tournament.tournamentService.repository.StudentRepository;
import com.polimi.ckb.tournament.tournamentService.repository.TournamentRepository;
import com.polimi.ckb.tournament.tournamentService.service.TournamentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

import static com.polimi.ckb.tournament.tournamentService.utility.score.InitScore.initScore;

@Service
@RequiredArgsConstructor
public class TournamentServiceImpl implements TournamentService {

    private final TournamentRepository tournamentRepository;
    private final StudentRepository studentRepository;
    private final ScoreRepository scoreRepository;

    @Transactional @Override
    public Tournament saveTournament(CreateTournamentDto msg) {
        Optional<Tournament> maybeTournament = tournamentRepository.findByName(msg.getName());
        if(maybeTournament.isPresent()) {
            //tournament already exist
            throw new TournamentAlreadyExistException();
        }
        return tournamentRepository.save(convertToEntity(msg));
    }

    @Transactional(readOnly = true) @Override
    public Tournament getTournament(Long id) {
        return tournamentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tournament not found"));
    }

    @Transactional @Override
    public Score updateTournamentScore(UpdateStudentScoreInTournamentDto msg) {
        Long tournamentId = msg.getTournamentId();
        Long studentId = msg.getStudentId();
        Integer score = msg.getScore();

        //Tournament exist
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new EntityNotFoundException("Tournament not found for battle id: " + tournamentId));

        //Student exist
        Student student = studentRepository.findById(studentId)
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + studentId));

        //Student is part of the tournament
        boolean isStudentParticipating = student.getTournaments().stream()
                .anyMatch(t -> t.getTournamentId().equals(tournamentId));

        if (!isStudentParticipating) {
            throw new IllegalArgumentException("Student with id " + studentId + " is not part of the tournament.");
        }

        Score studentScore = tournament.getScores().stream()
                .filter(s -> s.getStudent().getStudentId().equals(studentId))
                .findFirst()
                /* If the student has no score for the tournament, create a new one with 0 score*/
                .orElseGet(() -> initScore(student, tournament));

        studentScore.setScoreValue(studentScore.getScoreValue() + score);

        return scoreRepository.save(studentScore);
    }

    private Tournament convertToEntity(CreateTournamentDto createTournamentDto) {
        return Tournament.builder()
                .name(createTournamentDto.getName())
                .creatorId(createTournamentDto.getCreatorId())
                .registrationDeadline(createTournamentDto.getRegistrationDeadline())
                .status(TournamentStatus.PREPARATION)
                .build();
    }
}
