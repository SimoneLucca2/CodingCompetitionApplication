package com.polimi.ckb.battleService.utility.messageValidator.annotation;

import com.polimi.ckb.battleService.utility.messageValidator.BattleExistsValidator;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BattleExistsValidator.class)
public @interface BattleExists {
    String message() default "Battle does not exist";

    Class<?>[] groups() default {};

    Class<? extends javax.validation.Payload>[] payload() default {};
}
