package com.polimi.ckb.tournament.tournamentService.controller;

import com.polimi.ckb.tournament.tournamentService.dto.AddEducatorMessage;
import com.polimi.ckb.tournament.tournamentService.entity.Educator;
import com.polimi.ckb.tournament.tournamentService.service.EducatorService;
import com.polimi.ckb.tournament.tournamentService.service.kafkaProducer.AddEducatorKafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tournament/educator")
public class EducatorController {

    private final EducatorService educatorService;
    private final AddEducatorKafkaProducer kafkaProducer;

    @PostMapping
    public ResponseEntity<?> addEducator(@Valid @RequestBody AddEducatorMessage msg) {
        try {
            Educator response = educatorService.addEducatorToTournament(msg);
            kafkaProducer.sendAddedEducatorMessage(msg);
            return ResponseEntity.ok(response);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }


}
