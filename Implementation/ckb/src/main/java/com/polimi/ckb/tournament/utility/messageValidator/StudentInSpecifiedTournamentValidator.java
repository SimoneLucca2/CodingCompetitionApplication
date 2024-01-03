package com.polimi.ckb.tournament.utility.messageValidator;

import com.polimi.ckb.tournament.dto.StudentJoinTournamentDto;
import com.polimi.ckb.tournament.entity.Student;
import com.polimi.ckb.tournament.repository.StudentRepository;
import com.polimi.ckb.tournament.utility.messageValidator.annotation.StudentInSpecifiedTournament;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class StudentInSpecifiedTournamentValidator implements ConstraintValidator<StudentInSpecifiedTournament, StudentJoinTournamentDto> {

    private StudentRepository studentRepository;

    @Override
    public boolean isValid(StudentJoinTournamentDto studentJoinTournamentDto, ConstraintValidatorContext context) {
        Long studentId = studentJoinTournamentDto.getStudentId();
        Long tournamentId = studentJoinTournamentDto.getTournamentId();

        Student s = studentRepository.findById(studentId).orElse(null);
        return s != null && s.getTournaments().stream().anyMatch(t -> t.getTournamentId().equals(tournamentId));
    }
}
