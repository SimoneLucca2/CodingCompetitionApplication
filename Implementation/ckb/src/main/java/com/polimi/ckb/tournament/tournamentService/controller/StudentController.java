package com.polimi.ckb.tournament.tournamentService.controller;

import com.polimi.ckb.tournament.tournamentService.dto.StudentJoinTournamentDto;
import com.polimi.ckb.tournament.tournamentService.dto.StudentQuitTournamentDto;
import com.polimi.ckb.tournament.tournamentService.entity.Tournament;
import com.polimi.ckb.tournament.tournamentService.service.TournamentService;
import com.polimi.ckb.tournament.tournamentService.service.kafkaProducer.StudentJoinTournamentKafkaProducer;
import com.polimi.ckb.tournament.tournamentService.service.kafkaProducer.StudentQuitTournamentKafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tournament/student")
public class StudentController {

    private final TournamentService tournamentService;
    private final StudentJoinTournamentKafkaProducer joinKafkaProducer;
    private final StudentQuitTournamentKafkaProducer quitKafkaProducer;

    @PostMapping
    public ResponseEntity<Object> joinTournament(@Valid @RequestBody StudentJoinTournamentDto msg) {
        try {
            Tournament tournament = tournamentService.joinTournament(msg);
            joinKafkaProducer.sendStudentJoinMessage(msg);
            return ResponseEntity.ok(tournament);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<Object> leaveTournament(@Valid @RequestBody StudentQuitTournamentDto msg) {
        try {
            Tournament tournament = tournamentService.leaveTournament(msg);
            quitKafkaProducer.sendStudentQuitMessage(msg);
            return ResponseEntity.ok(tournament);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

}
