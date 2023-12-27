package com.polimi.ckb.tournament.tournamentService.utility.messageValidator;

import com.polimi.ckb.tournament.tournamentService.utility.messageValidator.annotation.ValidRegistrationDeadline;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class RegistrationDeadlineValidator implements ConstraintValidator<ValidRegistrationDeadline, String> {

    @Override
    public void initialize(ValidRegistrationDeadline constraintAnnotation) {
    }

    @Override
    public boolean isValid(String registrationDeadlineField, ConstraintValidatorContext context) {
        if (registrationDeadlineField == null) {
            return true;
        }

        try {
            LocalDateTime deadline = LocalDateTime.parse(registrationDeadlineField, DateTimeFormatter.ISO_DATE_TIME);
            return !deadline.isBefore(LocalDateTime.now());
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}