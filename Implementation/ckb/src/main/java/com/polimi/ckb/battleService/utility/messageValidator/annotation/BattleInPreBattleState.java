package com.polimi.ckb.battleService.utility.messageValidator.annotation;

import com.polimi.ckb.battleService.utility.messageValidator.BattleInPreBattleStateValidator;

import javax.validation.Constraint;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target({ElementType.METHOD, ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = BattleInPreBattleStateValidator.class)
public @interface BattleInPreBattleState {
    String message() default "Battle is not in pre-battle state";

    Class<?>[] groups() default {};

    Class<? extends javax.validation.Payload>[] payload() default {};
}
