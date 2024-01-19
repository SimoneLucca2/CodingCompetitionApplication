package com.polimi.ckb.user.service.impl;

import com.polimi.ckb.user.dto.EducatorJoinTournamentDto;
import com.polimi.ckb.user.dto.StudentJoinTournamentDto;
import com.polimi.ckb.user.dto.StudentQuitsTournamentDto;
import com.polimi.ckb.user.entity.Educator;
import com.polimi.ckb.user.entity.Student;
import com.polimi.ckb.user.entity.Tournament;
import com.polimi.ckb.user.repository.EducatorRepository;
import com.polimi.ckb.user.repository.StudentRepository;
import com.polimi.ckb.user.repository.TournamentRepository;
import com.polimi.ckb.user.service.TournamentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.Valid;

@Service
@RequiredArgsConstructor
public class TournamentServiceImpl implements TournamentService {

    private final TournamentRepository tournamentRepository;
    private final StudentRepository studentRepository;
    private final EducatorRepository educatorRepository;

    @Override
    public Tournament saveTournament(Tournament tournament) {
        return tournamentRepository.save(tournament);
    }

    /**
     * This method is used to add a student to a tournament.
     *
     * @param msg The DTO object containing the studentId and tournamentId.
     * @return The updated tournament object after adding the student.
     * @throws RuntimeException If the student or tournament is not found.
     */
    @Override
    public Tournament studentJoinTournament(@Valid StudentJoinTournamentDto msg) {

        //Get the student and tournament objects from the database
        Student student = studentRepository.findById(msg.getStudentId()).orElseThrow(() -> new RuntimeException("Student not found"));
        Tournament tournament = tournamentRepository.findById(msg.getTournamentId()).orElseThrow(() -> new RuntimeException("Tournament not found"));

        //Add the student to the tournament and save
        tournament.getStudents().add(student);
        tournamentRepository.save(tournament);

        return tournament;
    }

    /**
     * This method is used to remove a student from a tournament.
     *
     * @param msg The DTO object containing the studentId and tournamentId.
     * @return The updated tournament object after removing the student.
     * @throws RuntimeException If the student or tournament is not found.
     */
    @Override
    public Tournament studentQuitsTournament(@Valid StudentQuitsTournamentDto msg) {

        //Get the student and tournament objects from the database
        Student student = studentRepository.findById(msg.getStudentId()).orElseThrow(() -> new RuntimeException("Student not found"));
        Tournament tournament = tournamentRepository.findById(msg.getTournamentId()).orElseThrow(() -> new RuntimeException("Tournament not found"));

        //Remove the student from the tournament and save
        tournament.getStudents().remove(student);
        tournamentRepository.save(tournament);

        return tournament;
    }

    /**
     * This method is used to add an educator to a tournament.
     *
     * @param message The DTO object containing the educatorId and tournamentId.
     * @return The updated tournament object after adding the educator.
     * @throws RuntimeException If the educator or tournament is not found.
     */
    @Override
    public Tournament addEducatorToTournament(EducatorJoinTournamentDto message) {

        Tournament tournament = tournamentRepository.findById(message.getTournamentId())
                .orElseThrow(() -> new RuntimeException("Tournament not found"));

        //The educator must exist in the database
        Educator educator = educatorRepository.findById(message.getEducatorId())
                .orElseThrow(() -> new RuntimeException("Educator not found"));

        // check if educator is already part of the tournament
        if (tournament.getOrganizers().contains(educator)) throw new RuntimeException("Educator already part of the tournament");

        // add educator to tournament and save
        tournament.getOrganizers().add(educator);
        tournamentRepository.save(tournament);
        return tournament;
    }

}
