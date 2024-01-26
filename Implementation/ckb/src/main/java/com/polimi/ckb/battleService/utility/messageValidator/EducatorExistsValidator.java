package com.polimi.ckb.battleService.utility.messageValidator;

import com.polimi.ckb.battleService.repository.EducatorRepository;
import com.polimi.ckb.battleService.utility.messageValidator.annotation.EducatorExists;
import lombok.AllArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@AllArgsConstructor
public class EducatorExistsValidator implements ConstraintValidator<EducatorExists, Long> {
    private final EducatorRepository educatorRepository;

    @Override
    public boolean isValid(Long educatorId, ConstraintValidatorContext constraintValidatorContext) {
        return educatorRepository.existsById(educatorId);
    }
}
