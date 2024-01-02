package com.polimi.ckb.tournament.tournamentService.utility.messageValidator.annotation;

import com.polimi.ckb.tournament.tournamentService.utility.messageValidator.TournamentInPrepareStateValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.FIELD, ElementType.METHOD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TournamentInPrepareStateValidator.class)
public @interface TournamentInPrepareState {

    String message() default "Tournament is not in PREPARE state";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
