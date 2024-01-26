package com.polimi.ckb.tournament.controller;

import com.polimi.ckb.tournament.dto.AddEducatorDto;
import com.polimi.ckb.tournament.dto.TournamentDto;
import com.polimi.ckb.tournament.entity.Tournament;
import com.polimi.ckb.tournament.service.EducatorService;
import com.polimi.ckb.tournament.service.kafkaProducer.AddEducatorKafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tournament/educator")
@Slf4j
public class EducatorController {

    private final EducatorService educatorService;
    private final AddEducatorKafkaProducer kafkaProducer;

    /**
     * Adds an educator to a tournament.
     *
     * @param msg the AddEducatorDto object containing the necessary information for adding an educator to a tournament
     * @return a ResponseEntity containing the Educator object if the educator was successfully added, otherwise an error message
     */
    @PostMapping
    public ResponseEntity<Object> addEducator(@Valid @RequestBody AddEducatorDto msg) {
        try {
            Tournament response = educatorService.addEducatorToTournament(msg);
            kafkaProducer.sendAddedEducatorMessage(msg); //TODO probably remove
            return ResponseEntity.ok(TournamentDto.fromEntity(response));
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }


}
