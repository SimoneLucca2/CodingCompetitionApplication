package com.polimi.ckb.tournament.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.polimi.ckb.tournament.dto.*;
import com.polimi.ckb.tournament.entity.Tournament;
import com.polimi.ckb.tournament.service.TournamentService;
import com.polimi.ckb.tournament.service.kafkaProducer.TournamentCreationKafkaProducer;
import lombok.RequiredArgsConstructor;
import org.hibernate.validator.constraints.URL;
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
    public ResponseEntity<Object> createTournament(@Valid @RequestBody CreateTournamentDto msg) {
        try {
            log.info("Creating tournament with message: {}", msg);
            Tournament createdTournament = tournamentService.saveTournament(msg);

            CreatedTournamentKafkaDto kafkaMsg = CreatedTournamentKafkaDto.builder()
                    .creatorId(msg.getCreatorId())
                    .tournamentId(createdTournament.getTournamentId())
                    .name(msg.getName())
                    .registrationDeadline(msg.getRegistrationDeadline())
                    .build();

            kafkaProducer.sendTournamentCreationMessage(kafkaMsg);
            log.info("Tournament created successfully");
            return ResponseEntity.ok(TournamentDto.fromEntity(createdTournament));
        } catch (JsonProcessingException e) {
            log.error("Error processing JSON: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(new ErrorResponse("Error processing JSON: " + e.getMessage()));
        } catch (Exception e) {
            log.error("Internal server error: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(new ErrorResponse("Internal server error: " + e.getMessage()));
        }
    }

    @GetMapping("/{tournamentId}")
    public ResponseEntity<Object> getTournament(@PathVariable Long tournamentId) {
        try {
            log.info("Getting tournament with id: {}", tournamentId);
            Tournament tournament = tournamentService.getTournament(tournamentId);
            log.info("Tournament retrieved successfully");
            return ResponseEntity.ok(TournamentDto.fromEntity(tournament));
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
