package com.polimi.ckb.tournament.tournamentService.utility.messageValidator.annotation;

import com.polimi.ckb.tournament.tournamentService.utility.messageValidator.TournamentExistsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TournamentExistsValidator.class)
public @interface TournamentExists {

    String message() default "the tournament does not exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}