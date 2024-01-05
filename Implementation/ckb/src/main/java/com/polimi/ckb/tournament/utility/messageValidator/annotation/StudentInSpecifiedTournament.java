package com.polimi.ckb.tournament.utility.messageValidator.annotation;

import com.polimi.ckb.tournament.utility.messageValidator.StudentInSpecifiedTournamentValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StudentInSpecifiedTournamentValidator.class)
public @interface StudentInSpecifiedTournament {

    String message() default "Student is not in the specified tournament";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
