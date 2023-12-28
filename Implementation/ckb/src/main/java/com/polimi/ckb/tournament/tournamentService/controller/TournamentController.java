package com.polimi.ckb.tournament.tournamentService.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.polimi.ckb.tournament.tournamentService.dto.CreateTournamentMessage;
import com.polimi.ckb.tournament.tournamentService.dto.ErrorResponse;
import com.polimi.ckb.tournament.tournamentService.entity.Tournament;
import com.polimi.ckb.tournament.tournamentService.service.TournamentCreationKafkaProducer;
import com.polimi.ckb.tournament.tournamentService.service.TournamentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tournament")
public class TournamentController {
    private final TournamentService tournamentService;
    private final TournamentCreationKafkaProducer kafkaProducer;

    private static final Logger log = LoggerFactory.getLogger(TournamentController.class);

    @PostMapping
    public ResponseEntity<Object> createTournament(@Valid @RequestBody CreateTournamentMessage msg) {
        try {
            log.info("Creating tournament with message: {}", msg);
            Tournament createdTournament = tournamentService.saveTournament(msg);
            kafkaProducer.sendTournamentCreationMessage(msg);
            log.info("Tournament created successfully");
            return ResponseEntity.ok(createdTournament);
        } catch (JsonProcessingException e) {
            log.error("Error processing JSON: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(new ErrorResponse("Error processing JSON: " + e.getMessage()));
        } catch (Exception e) {
            log.error("Internal server error: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(new ErrorResponse("Internal server error: " + e.getMessage()));
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        log.error("Validation error: {}", ex.getMessage());
        return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage()));
    }

}
