package com.polimi.ckb.battleService.utility.messageValidator;

import com.polimi.ckb.battleService.config.BattleStatus;
import com.polimi.ckb.battleService.entity.Battle;
import com.polimi.ckb.battleService.repository.BattleRepository;
import com.polimi.ckb.battleService.utility.messageValidator.annotation.BattleInPreBattleState;
import lombok.AllArgsConstructor;

import javax.validation.ConstraintValidator;

@AllArgsConstructor
public class BattleInPreBattleStateValidator implements ConstraintValidator<BattleInPreBattleState, Long> {
    private final BattleRepository battleRepository;

    @Override
    public boolean isValid(Long battleId, javax.validation.ConstraintValidatorContext context) {
        Battle battle = battleRepository.findById(battleId).orElse(null);
        return battle != null && battle.getStatus().equals(BattleStatus.PRE_BATTLE);
    }
}
