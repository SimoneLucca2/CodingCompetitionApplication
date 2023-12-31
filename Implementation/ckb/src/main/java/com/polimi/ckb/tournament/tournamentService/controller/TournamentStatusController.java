package com.polimi.ckb.tournament.tournamentService.controller;

import com.polimi.ckb.tournament.tournamentService.dto.ChangeTournamentStatusDto;
import com.polimi.ckb.tournament.tournamentService.entity.Tournament;
import com.polimi.ckb.tournament.tournamentService.service.TournamentStatusService;
import com.polimi.ckb.tournament.tournamentService.service.kafkaProducer.TournamentStatusKafkaProducer;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tournament/status")
public class TournamentStatusController {

    private final TournamentStatusService tournamentStatusService;
    private final TournamentStatusKafkaProducer kafkaProducer;

    /**
     * Updates the status of a tournament.
     *
     * @param msg the change tournament status message object containing the necessary information
     * @return a ResponseEntity representing the response of the API call
     */
    @PutMapping
    public ResponseEntity<Object> updateTournamentStatus(@RequestBody @Valid ChangeTournamentStatusDto msg) {
        try{
            Tournament createdTournament = tournamentStatusService.updateTournamentStatus(msg);
            kafkaProducer.sendTournamentMessage(msg);
            return ResponseEntity.ok(createdTournament);
        }catch (Exception e){
            return ResponseEntity.badRequest().build();
        }
    }
}
