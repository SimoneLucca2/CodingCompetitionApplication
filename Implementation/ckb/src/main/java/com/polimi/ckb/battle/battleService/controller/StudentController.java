package com.polimi.ckb.battle.battleService.controller;

import com.polimi.ckb.battle.battleService.dto.StudentJoinBattleDto;
import com.polimi.ckb.battle.battleService.dto.StudentLeaveBattleDto;
import com.polimi.ckb.battle.battleService.service.impl.StudentServiceImpl;
import com.polimi.ckb.battle.battleService.service.kafkaProducer.StudentJoinBattleProducer;
import com.polimi.ckb.battle.battleService.service.kafkaProducer.StudentLeaveBattleProducer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/battle/student")
@AllArgsConstructor
@Slf4j
public class StudentController {
    private final StudentServiceImpl studentServiceImpl;
    private final StudentJoinBattleProducer studentJoinBattleProducer;
    private final StudentLeaveBattleProducer studentLeaveBattleProducer;

    @PostMapping
    public ResponseEntity<Object> joinBattle(@RequestBody StudentJoinBattleDto studentDto){
        return null;
    }

    @DeleteMapping
    public ResponseEntity<Object> leaveBattle(@RequestBody StudentLeaveBattleDto studentDto){
        return null;
    }
}
