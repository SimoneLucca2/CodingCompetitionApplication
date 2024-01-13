package com.polimi.ckb.battleService.utility.messageValidator;

import com.polimi.ckb.battleService.repository.StudentRepository;
import com.polimi.ckb.battleService.utility.messageValidator.annotation.StudentExists;
import lombok.AllArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@AllArgsConstructor
public class StudentExistsValidator implements ConstraintValidator<StudentExists, Long> {
    private final StudentRepository studentRepository;

    @Override
    public boolean isValid(Long studentId, ConstraintValidatorContext constraintValidatorContext) {
        return studentRepository.existsById(studentId);
    }
}
