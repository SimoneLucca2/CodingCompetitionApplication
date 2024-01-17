package com.polimi.ckb.apiGateway.utility.annotations.validator;

import com.polimi.ckb.apiGateway.repository.UserRepository;
import com.polimi.ckb.apiGateway.utility.annotations.EmailNotInUse;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.RequiredArgsConstructor;

@RequiredArgsConstructor
public class EmailNotInUseValidator implements ConstraintValidator<EmailNotInUse, String> {

    private final UserRepository userRepository;

    @Override
    public boolean isValid(String email, ConstraintValidatorContext context) {
        return !userRepository.existsById(email);
    }
}
