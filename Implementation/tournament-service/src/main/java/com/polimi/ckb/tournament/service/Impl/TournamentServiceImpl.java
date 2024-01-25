package com.polimi.ckb.tournament.service.Impl;

import com.polimi.ckb.tournament.config.TournamentStatus;
import com.polimi.ckb.tournament.dto.CreateTournamentDto;
import com.polimi.ckb.tournament.dto.StudentJoinTournamentDto;
import com.polimi.ckb.tournament.dto.StudentQuitTournamentDto;
import com.polimi.ckb.tournament.dto.UpdateStudentScoreInTournamentDto;
import com.polimi.ckb.tournament.entity.Score;
import com.polimi.ckb.tournament.entity.Student;
import com.polimi.ckb.tournament.entity.Tournament;
import com.polimi.ckb.tournament.exception.TournamentAlreadyExistException;
import com.polimi.ckb.tournament.repository.ScoreRepository;
import com.polimi.ckb.tournament.repository.StudentRepository;
import com.polimi.ckb.tournament.repository.TournamentRepository;
import com.polimi.ckb.tournament.service.TournamentService;
import com.polimi.ckb.tournament.utility.entityConverter.CreateTournamentDtoToTournament;
import com.polimi.ckb.tournament.utility.score.InitScore;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TournamentServiceImpl implements TournamentService {

    private final TournamentRepository tournamentRepository;
    private final StudentRepository studentRepository;
    private final ScoreRepository scoreRepository;

    @Transactional
    @Override
    public Tournament saveTournament(CreateTournamentDto msg) {
        Optional<Tournament> maybeTournament = tournamentRepository.findByName(msg.getName());
        if (maybeTournament.isPresent()) {
            //tournament already exist
            throw new TournamentAlreadyExistException();
        }
        return tournamentRepository.save(CreateTournamentDtoToTournament.convertToEntity(msg));
    }

    @Transactional(readOnly = true)
    @Override
    public Tournament getTournament(Long id) {
        return tournamentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tournament not found"));
    }

    @Transactional
    @Override
    public void updateTournamentScore(UpdateStudentScoreInTournamentDto msg) {
        Long tournamentId = msg.getTournamentId();
        Long studentId = msg.getStudentId();
        Integer score = msg.getScore();

        //Tournament exist
        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new EntityNotFoundException("Tournament not found with id: " + tournamentId));

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
                .orElseGet(() -> InitScore.initScore(student, tournament, scoreRepository));

        studentScore.setScoreValue(studentScore.getScoreValue() + score);

        scoreRepository.save(studentScore);
    }

    public List<Tournament> getAllTournaments() {
        return tournamentRepository.findAll();
    }

    @Transactional
    @Override
    public List<Tournament> getPreparationTournaments() {
        return tournamentRepository.getInPreparationTournaments();
    }

    @Transactional
    @Override
    public List<Tournament> getActiveTournaments() {
        return tournamentRepository.getActiveTournaments();
    }

    @Transactional
    @Override
    public Tournament joinTournament(@Valid StudentJoinTournamentDto msg) {

        Tournament tournament = tournamentRepository.findById(msg.getTournamentId())
                .orElseThrow(() -> new EntityNotFoundException("Tournament not found with id: " + msg.getTournamentId()));

        if (tournament.getStatus() != TournamentStatus.PREPARATION) {
            throw new IllegalArgumentException("Tournament is not open for registration");
        }

        Student student = studentRepository.findById(msg.getStudentId())
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + msg.getStudentId()));


        student.getTournaments().add(tournament);

        return tournamentRepository.save(tournament);
    }

    @Override
    @Transactional
    public Tournament leaveTournament(StudentQuitTournamentDto msg) {
        Student student = studentRepository.findById(msg.getStudentId())
                .orElseThrow(() -> new EntityNotFoundException("Student not found with id: " + msg.getStudentId()));

        Tournament tournament = tournamentRepository.findById(msg.getTournamentId())
                .orElseThrow(() -> new EntityNotFoundException("Tournament not found with id: " + msg.getTournamentId()));

        student.getTournaments().remove(tournament);

        return tournamentRepository.save(tournament);
    }

}
