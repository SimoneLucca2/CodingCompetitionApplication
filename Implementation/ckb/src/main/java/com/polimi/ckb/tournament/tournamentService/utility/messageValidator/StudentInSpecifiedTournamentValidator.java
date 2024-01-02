package com.polimi.ckb.tournament.tournamentService.utility.messageValidator;

import com.polimi.ckb.tournament.tournamentService.dto.StudentJoinDto;
import com.polimi.ckb.tournament.tournamentService.entity.Student;
import com.polimi.ckb.tournament.tournamentService.repository.StudentRepository;
import com.polimi.ckb.tournament.tournamentService.utility.messageValidator.annotation.StudentInSpecifiedTournament;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class StudentInSpecifiedTournamentValidator implements ConstraintValidator<StudentInSpecifiedTournament, StudentJoinDto> {

    private StudentRepository studentRepository;

    @Override
    public boolean isValid(StudentJoinDto studentJoinDto, ConstraintValidatorContext context) {
        Long studentId = studentJoinDto.getStudentId();
        Long tournamentId = studentJoinDto.getTournamentId();

        Student s = studentRepository.findById(studentId).orElse(null);
        return s != null && s.getTournaments().stream().anyMatch(t -> t.getTournamentId().equals(tournamentId));
    }
}
