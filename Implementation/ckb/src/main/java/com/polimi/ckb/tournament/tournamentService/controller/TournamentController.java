package com.polimi.ckb.tournament.tournamentService.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.polimi.ckb.tournament.tournamentService.dto.CreateTournamentMessage;
import com.polimi.ckb.tournament.tournamentService.dto.ErrorResponse;
import com.polimi.ckb.tournament.tournamentService.entity.Tournament;
import com.polimi.ckb.tournament.tournamentService.service.TournamentKafkaProducer;
import com.polimi.ckb.tournament.tournamentService.service.TournamentService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequestMapping("/tournament")
public class TournamentController {

    private final TournamentService tournamentService;
    private final TournamentKafkaProducer kafkaProducer;

    @Autowired
    public TournamentController(TournamentService tournamentService, TournamentKafkaProducer kafkaProducer) {
        this.tournamentService = tournamentService;
        this.kafkaProducer = kafkaProducer;
    }

    @PostMapping
    public ResponseEntity<Object> createTournament(@Valid @RequestBody CreateTournamentMessage msg) {
        try {
            Tournament createdTournament = tournamentService.createTournament(msg);
            kafkaProducer.sendTournamentCreationMessage(msg);
            return ResponseEntity.ok(createdTournament);
        } catch (JsonProcessingException e) {
            return ResponseEntity.internalServerError().body(new ErrorResponse("Error processing JSON: " + e.getMessage()));
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ErrorResponse("Internal server error: " + e.getMessage()));
        }
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Object> handleValidationExceptions(MethodArgumentNotValidException ex) {
        return ResponseEntity.badRequest().body(new ErrorResponse(ex.getMessage()));
    }

}
