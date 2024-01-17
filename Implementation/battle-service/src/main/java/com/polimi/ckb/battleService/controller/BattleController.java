package com.polimi.ckb.battleService.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.polimi.ckb.battleService.dto.CreateBattleDto;
import com.polimi.ckb.battleService.dto.ErrorResponse;
import com.polimi.ckb.battleService.entity.Battle;
import com.polimi.ckb.battleService.exception.BattleAlreadyExistException;
import com.polimi.ckb.battleService.exception.BattleDeadlinesOverlapException;
import com.polimi.ckb.battleService.service.BattleService;
import com.polimi.ckb.battleService.service.kafkaProducer.BattleCreationKafkaProducer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@AllArgsConstructor
@RequestMapping(path = "/battle")
@Slf4j
public class BattleController {
    private final BattleService battleService;
    private final BattleCreationKafkaProducer kafkaProducer;

    @PostMapping
    public ResponseEntity<Object> createBattle(@RequestBody @Valid CreateBattleDto createBattleDto){
       try{
           log.info("Creating battle with message: {" + createBattleDto + "}");
           if(createBattleDto.getSubmissionDeadline().compareTo(createBattleDto.getRegistrationDeadline()) <= 0){
               log.error("Submission deadline is before registration deadline or they are the same");
               return ResponseEntity.badRequest().body(new ErrorResponse("Submission deadline is before registration deadline or they are the same"));
           }
           if(createBattleDto.getMaxGroupSize() < createBattleDto.getMinGroupSize()){
               log.error("Max group size is less than min group size");
               return ResponseEntity.badRequest().body(new ErrorResponse("Max group size is less than min group size"));
           }
           Battle createdBattle = battleService.createBattle(createBattleDto);

           //TODO: create github repository for the battle using GitHub API

           kafkaProducer.sendBattleCreationMessage(createBattleDto);
           log.info("Battle created successfully");
           return ResponseEntity.ok().body(createdBattle);
       } catch (BattleAlreadyExistException | BattleDeadlinesOverlapException e){
           log.error("Bad request: {}", e.getMessage());
           return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
       } catch (JsonProcessingException e) {
           log.error("Error processing JSON: {}", e.getMessage());
           return ResponseEntity.internalServerError().body(new ErrorResponse("Error processing JSON: " + e.getMessage()));
       }
    }
}
