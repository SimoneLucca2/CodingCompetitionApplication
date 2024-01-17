package com.polimi.ckb.apiGateway.utility.annotations;

import com.polimi.ckb.apiGateway.utility.annotations.validator.EmailNotInUseValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;


@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = EmailNotInUseValidator.class)
public @interface EmailNotInUse {
    String message() default "the email is already in use";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}

