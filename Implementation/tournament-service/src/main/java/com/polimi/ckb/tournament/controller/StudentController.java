package com.polimi.ckb.tournament.controller;

import com.polimi.ckb.tournament.dto.StudentJoinTournamentDto;
import com.polimi.ckb.tournament.dto.StudentQuitTournamentDto;
import com.polimi.ckb.tournament.dto.TournamentDto;
import com.polimi.ckb.tournament.entity.Tournament;
import com.polimi.ckb.tournament.service.TournamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tournament/student")
public class StudentController {

    private final TournamentService tournamentService;

    @PostMapping
    public ResponseEntity<Object> joinTournament(@Valid @RequestBody StudentJoinTournamentDto msg) {
        try {
            Tournament tournament = tournamentService.joinTournament(msg);
            return ResponseEntity.ok(TournamentDto.fromEntity(tournament));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

    @DeleteMapping
    public ResponseEntity<Object> leaveTournament(@Valid @RequestBody StudentQuitTournamentDto msg) {
        try {
            Tournament tournament = tournamentService.leaveTournament(msg);
            return ResponseEntity.ok(TournamentDto.fromEntity(tournament));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

}
