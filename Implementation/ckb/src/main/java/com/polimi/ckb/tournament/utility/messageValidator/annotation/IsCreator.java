package com.polimi.ckb.tournament.utility.messageValidator.annotation;

import com.polimi.ckb.tournament.utility.messageValidator.IsCreatorValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = IsCreatorValidator.class)
public @interface IsCreator {

    String message() default "To add a student to a tournament, you must be the creator of the tournament.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}