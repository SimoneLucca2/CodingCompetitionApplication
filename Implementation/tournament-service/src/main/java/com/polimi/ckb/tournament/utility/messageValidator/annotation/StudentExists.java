package com.polimi.ckb.tournament.utility.messageValidator.annotation;

import com.polimi.ckb.tournament.utility.messageValidator.StudentExistsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = StudentExistsValidator.class)
public @interface StudentExists {

    String message() default "the student does not exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}