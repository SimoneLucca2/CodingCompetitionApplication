package com.polimi.ckb.tournament.utility.messageValidator;

import com.polimi.ckb.tournament.utility.messageValidator.annotation.ValidRegistrationDeadline;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.time.OffsetDateTime;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;

public class RegistrationDeadlineValidator implements ConstraintValidator<ValidRegistrationDeadline, String> {

    @Override
    public void initialize(ValidRegistrationDeadline constraintAnnotation) {
    }

    @Override
    public boolean isValid(String registrationDeadlineField, ConstraintValidatorContext context) {
        try {
            OffsetDateTime deadline = OffsetDateTime.parse(registrationDeadlineField, DateTimeFormatter.ISO_DATE_TIME);
            return !deadline.isBefore(OffsetDateTime.now());
        } catch (DateTimeParseException e) {
            return false;
        }
    }
}