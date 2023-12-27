package com.polimi.ckb.tournament.tournamentService.utility.messageValidator;

import com.polimi.ckb.tournament.tournamentService.utility.messageValidator.annotation.ValidStatus;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Arrays;

public class StatusValidator implements ConstraintValidator<ValidStatus, String> {

    private static final String[] validStatuses = {"prepare", "active", "closing", "closed"};

    @Override
    public void initialize(ValidStatus status) {
    }

    @Override
    public boolean isValid(String statusField, ConstraintValidatorContext context) {
        return statusField != null && Arrays.asList(validStatuses).contains(statusField);
    }
}