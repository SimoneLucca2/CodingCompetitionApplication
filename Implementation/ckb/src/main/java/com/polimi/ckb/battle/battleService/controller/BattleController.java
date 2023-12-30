package com.polimi.ckb.battle.battleService.controller;

import com.polimi.ckb.battle.battleService.dto.CreateBattleMessage;
import com.polimi.ckb.battle.battleService.entity.BattleEntity;
import com.polimi.ckb.battle.battleService.service.BattleService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
public class BattleController {
    private final BattleService battleService;
    //kafka producer

    private static final Logger log = LoggerFactory.getLogger(BattleController.class);

    @PostMapping(path = "/battle")
    public ResponseEntity<BattleEntity> createBattle(@RequestBody CreateBattleMessage msg){
        try{
            log.info("Creating battle with messahe: {}", msg);
            BattleEntity createdBattle = battleService.saveBattle(msg);
            //TODO: kafka producer
            log.info("Battle created successfully");
            return ResponseEntity.ok(createdBattle);
        } catch (){

        }
    }
}
