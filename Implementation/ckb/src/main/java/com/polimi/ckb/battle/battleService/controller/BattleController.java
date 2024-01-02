package com.polimi.ckb.battle.battleService.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.polimi.ckb.battle.battleService.dto.BattleDto;
import com.polimi.ckb.battle.battleService.entity.Battle;
import com.polimi.ckb.battle.battleService.exception.BattleAlreadyExistException;
import com.polimi.ckb.battle.battleService.service.BattleService;
import com.polimi.ckb.battle.battleService.service.kafkaProducer.BattleCreationKafkaProducer;
import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/battle")
public class BattleController {
    private final BattleService battleService;
    private final BattleCreationKafkaProducer kafkaProducer;

    private static final Logger log = LoggerFactory.getLogger(BattleController.class);

    @PostMapping
    public ResponseEntity<Object> createBattle(@RequestBody BattleDto battleDto){
       try{
           log.info("Creating battle with message: {}", battleDto);
           //TODO: check data and groups constraints

           Battle createdBattle = battleService.saveBattle(battleDto);
           kafkaProducer.sendBattleCreationMessage(battleDto);
           log.info("Battle created successfully");
           return ResponseEntity.ok(createdBattle);
       } catch (BattleAlreadyExistException e){
           log.error("Internal server error: {}", e.getMessage());
           return ResponseEntity.badRequest().build();
       } catch (JsonProcessingException e) {
           log.error("Error processing JSON: {}", e.getMessage());
           return ResponseEntity.internalServerError().build();
       }
    }
}
