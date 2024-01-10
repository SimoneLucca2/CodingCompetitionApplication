package com.polimi.ckb;

import com.polimi.ckb.battleService.entity.Battle;
import com.polimi.ckb.battleService.entity.Educator;
import com.polimi.ckb.battleService.entity.Student;

public final class BattleTestUtil {
    public BattleTestUtil(){

    }

    public static Battle createTestBattle(){
        return Battle.builder()
                .battleId(4L)
                .name("Test Battle")
                .description("Test Battle Description")
                .maxGroupSize(5)
                .minGroupSize(1)
                .registrationDeadline("2024-01-20")
                .submissionDeadline("2024-01-30")
                .tournamentId(10L)
                .build();
    }

    public static Battle createTestOverlappingBattle(){
        return Battle.builder()
                .battleId(4L)
                .name("Overlapping Battle")
                .description("Test Battle Description")
                .maxGroupSize(5)
                .minGroupSize(1)
                .registrationDeadline("2024-01-25")
                .submissionDeadline("2024-01-29")
                .tournamentId(10L)
                .build();
    }

    public static Educator createTestEducator(){
        return Educator.builder()
                .educatorId(1L)
                .build();
    }

    public static Student createTestStudent(){
        return Student.builder()
                .studentId(2L)
                .build();
    }
}
