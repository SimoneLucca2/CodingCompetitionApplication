package com.polimi.ckb.tournament.controller;

import com.polimi.ckb.tournament.dto.ErrorResponse;
import com.polimi.ckb.tournament.dto.GetTournamentRankingDto;
import com.polimi.ckb.tournament.dto.RankingEntryDto;
import com.polimi.ckb.tournament.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tournament/ranking")
public class TournamentRankingController {

    private final RankingService rankingService;

    @GetMapping
    public ResponseEntity<Object> getTournamentRanking(@Valid @RequestBody GetTournamentRankingDto msg) {
        try {
            List<RankingEntryDto> ranking = rankingService.getTournamentRanking(msg.getTournamentId(), msg.getFirstIndex(), msg.getLastIndex());
            return ResponseEntity.ok(ranking);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ErrorResponse("Internal server error: " + e.getMessage()));
        }
    }

    @GetMapping("/{tournamentId}")
    public ResponseEntity<Object> getTournamentRankingPV(@PathVariable Long tournamentId) {
        try {
            List<RankingEntryDto> ranking = rankingService.getTournamentRanking(tournamentId, null, null);
            return ResponseEntity.ok(ranking);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ErrorResponse("Internal server error: " + e.getMessage()));
        }
    }

}
