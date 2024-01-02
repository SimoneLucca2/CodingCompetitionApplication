package com.polimi.ckb.battle.battleService.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.polimi.ckb.battle.battleService.dto.CreateBattleDto;
import com.polimi.ckb.battle.battleService.entity.Battle;
import com.polimi.ckb.battle.battleService.exception.BattleAlreadyExistException;
import com.polimi.ckb.battle.battleService.service.BattleService;
import com.polimi.ckb.battle.battleService.service.kafkaProducer.BattleCreationKafkaProducer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/battle")
@Slf4j
public class BattleController {
    private final BattleService battleService;
    private final BattleCreationKafkaProducer kafkaProducer;

    @PostMapping
    public ResponseEntity<Object> createBattle(@RequestBody CreateBattleDto createBattleDto){
       try{
           log.info("Creating battle with message: {" + createBattleDto + "}");
           if(createBattleDto.getSubmissionDeadline().compareTo(createBattleDto.getRegistrationDeadline()) <= 0){
               log.error("Submission deadline is before registration deadline or they are the same");
               return ResponseEntity.badRequest().build();
           }
           if(createBattleDto.getMaxGroupSize() < createBattleDto.getMinGroupSize()){
               log.error("Max group size is less than min group size");
               return ResponseEntity.badRequest().build();
           }
           Battle createdBattle = battleService.saveBattle(createBattleDto);
           kafkaProducer.sendBattleCreationMessage(createBattleDto);
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
