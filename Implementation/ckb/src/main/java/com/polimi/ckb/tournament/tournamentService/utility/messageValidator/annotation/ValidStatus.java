package com.polimi.ckb.tournament.tournamentService.utility.messageValidator.annotation;

import com.polimi.ckb.tournament.tournamentService.utility.messageValidator.StatusValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StatusValidator.class)
public @interface ValidStatus {

    String message() default "Invalid status. Status should be either 'prepare', 'active', 'closing', or 'closed'";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}