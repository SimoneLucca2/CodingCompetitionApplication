package com.polimi.ckb.battleService.utility.messageValidator;

import com.polimi.ckb.battleService.repository.BattleRepository;
import com.polimi.ckb.battleService.utility.messageValidator.annotation.BattleExists;
import lombok.AllArgsConstructor;

import javax.validation.ConstraintValidator;

@AllArgsConstructor
public class BattleExistsValidator implements ConstraintValidator<BattleExists, Long> {
    private final BattleRepository battleRepository;

    @Override
    public boolean isValid(Long battleId, javax.validation.ConstraintValidatorContext constraintValidatorContext) {
        return battleRepository.existsById(battleId);
    }
}
