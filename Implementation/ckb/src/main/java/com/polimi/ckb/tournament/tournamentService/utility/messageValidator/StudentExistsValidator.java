package com.polimi.ckb.tournament.tournamentService.utility.messageValidator;

import com.polimi.ckb.tournament.tournamentService.repository.StudentRepository;
import com.polimi.ckb.tournament.tournamentService.utility.messageValidator.annotation.StudentExists;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class StudentExistsValidator implements ConstraintValidator<StudentExists, Long> {

    private final StudentRepository studentRepository;

    @Override
    public boolean isValid(Long studentId, ConstraintValidatorContext context) {
        return studentRepository.existsById(studentId);
    }
}
