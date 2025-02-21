package com.polimi.ckb.battleService.utility;

import com.polimi.ckb.battleService.dto.CreateBattleDto;
import com.polimi.ckb.battleService.exception.BattleDeadlineException;
import com.polimi.ckb.battleService.exception.BattleGroupConstraintException;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class CreateBattleFilter {

    public static void check(CreateBattleDto createBattleDto) throws RuntimeException {

        //TODO: change thrown exceptions

        log.info("Creating battle with message: {" + createBattleDto + "}");
        if (createBattleDto.getSubmissionDeadline().compareTo(createBattleDto.getRegistrationDeadline()) <= 0) {
            log.error("Submission deadline is before registration deadline or they are the same");
            throw new BattleDeadlineException();
        }
        if (createBattleDto.getMaxGroupSize() < createBattleDto.getMinGroupSize()) {
            log.error("Max group size is less than min group size");
            throw new BattleGroupConstraintException();
        }
    }
}
