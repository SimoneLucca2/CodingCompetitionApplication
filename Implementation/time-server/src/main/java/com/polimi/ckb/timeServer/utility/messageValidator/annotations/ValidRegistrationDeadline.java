package com.polimi.ckb.timeServer.utility.messageValidator.annotations;


import com.polimi.ckb.timeServer.utility.messageValidator.RegistrationDeadlineValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = RegistrationDeadlineValidator.class)
public @interface ValidRegistrationDeadline {

    String message() default "Invalid 'registrationDeadline' format. It should be ISO-8601 formatted and future time.";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}