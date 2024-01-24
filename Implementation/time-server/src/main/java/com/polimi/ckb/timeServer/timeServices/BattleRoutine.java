package com.polimi.ckb.timeServer.timeServices;

import com.polimi.ckb.timeServer.config.BattleStatus;
import com.polimi.ckb.timeServer.entity.Battle;
import com.polimi.ckb.timeServer.repository.BattleRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Component
public class BattleRoutine {

    private final BattleRepository battleRepository;
    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");

    public BattleRoutine(BattleRepository battleRepository) {
        this.battleRepository = battleRepository;
    }

    @Scheduled(fixedRateString = "#{T(java.lang.Integer).valueOf('${battle.polling.rate}')}")
    @Async
    public void checkBattleRegistrationDeadline() {
        String currentTime = sdf.format(new Date());
        List<Battle> battles = battleRepository.findAllWithRegistrationDeadlinePassedAndStatusPreBattle(currentTime);
        battles.forEach(battle -> {
            battle.setStatus(BattleStatus.BATTLE);
            battleRepository.save(battle);
        });
    }

    @Scheduled(fixedRateString = "#{T(java.lang.Integer).valueOf('${battle.polling.rate}')}")
    @Async
    public void checkBattleSubmissionDeadline() {
        String currentTime = sdf.format(new Date());
        List<Battle> battles = battleRepository.findAllWithSubmissionDeadlinePassedAndStatusBattle(currentTime);
        battles.forEach(battle -> {
            battle.setStatus(BattleStatus.CONSOLIDATION);
            battleRepository.save(battle);
        });
    }
}

