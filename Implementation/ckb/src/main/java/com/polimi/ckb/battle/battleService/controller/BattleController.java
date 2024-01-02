package com.polimi.ckb.battle.battleService.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.polimi.ckb.battle.battleService.dto.BattleDto;
import com.polimi.ckb.battle.battleService.entity.Battle;
import com.polimi.ckb.battle.battleService.exception.BattleAlreadyExistException;
import com.polimi.ckb.battle.battleService.service.BattleService;
import com.polimi.ckb.battle.battleService.service.kafkaProducer.BattleCreationKafkaProducer;
import com.polimi.ckb.tournament.tournamentService.dto.ErrorResponse;
import lombok.AllArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/battle")
public class BattleController {
    private final BattleService battleService;
    private final BattleCreationKafkaProducer kafkaProducer;

    private static final Logger log = LoggerFactory.getLogger(BattleController.class);

    @PostMapping()
    public ResponseEntity<Object> createBattle(@RequestBody BattleDto battleDto){
       try{
           log.info("Creating battle with message: {}", battleDto);
           //TODO: check data and groups constraints

           Battle createdBattle = battleService.saveBattle(battleDto);
           kafkaProducer.sendBattleCreationMessage(battleDto);
           log.info("Battle created successfully");
           return ResponseEntity.ok(createdBattle);
       } catch (BattleAlreadyExistException e){
           log.error("Error processing JSON: {}", e.getMessage());
           return ResponseEntity.internalServerError().body(new ErrorResponse("Internal server error: " + e.getMessage()));
       } catch (JsonProcessingException e) {
           log.error("Internal server error: {}", e.getMessage());
           return ResponseEntity.internalServerError().body(new ErrorResponse("Error processing JSON: " + e.getMessage()));
       }
    }
}
