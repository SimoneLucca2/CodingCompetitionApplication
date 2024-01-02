package com.polimi.ckb.tournament.tournamentService.controller;

import com.polimi.ckb.tournament.tournamentService.dto.StudentJoinDto;
import com.polimi.ckb.tournament.tournamentService.entity.Tournament;
import com.polimi.ckb.tournament.tournamentService.service.TournamentService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tournament/student")
public class StudentController {

    private final TournamentService tournamentService;

    @PostMapping
    public ResponseEntity<Object> joinTournament(@Valid @RequestBody StudentJoinDto msg) {
        try {
            Tournament tournament = tournamentService.joinTournament(msg);
            return ResponseEntity.ok(tournament);
        } catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }

}
