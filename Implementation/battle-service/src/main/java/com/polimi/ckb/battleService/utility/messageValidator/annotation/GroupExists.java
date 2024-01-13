package com.polimi.ckb.battleService.utility.messageValidator.annotation;

import com.polimi.ckb.battleService.utility.messageValidator.GroupExistsValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = GroupExistsValidator.class)
public @interface GroupExists {
    String message() default "Educator does not exist";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
