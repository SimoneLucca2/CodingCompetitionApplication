package com.polimi.ckb.tournament.tournamentService.controller;

import com.polimi.ckb.tournament.tournamentService.dto.AddEducatorMessage;
import com.polimi.ckb.tournament.tournamentService.entity.Educator;
import com.polimi.ckb.tournament.tournamentService.service.EducatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/tournament/educator")
public class EducatorController {
    private final EducatorService educatorService;

    @Autowired
    public EducatorController(EducatorService educatorService) {
        this.educatorService = educatorService;
    }

    @PostMapping
    public ResponseEntity<?> addEducator(@RequestBody AddEducatorMessage msg) {
        try {
            Educator response = educatorService.addEducatorToTournament(msg);
            return ResponseEntity.ok(response);
        }catch (Exception e) {
            return ResponseEntity.badRequest().body("Error: " + e.getMessage());
        }
    }
}
