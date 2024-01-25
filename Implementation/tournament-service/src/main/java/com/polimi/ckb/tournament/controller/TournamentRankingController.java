package com.polimi.ckb.tournament.controller;

import com.polimi.ckb.tournament.dto.ErrorResponse;
import com.polimi.ckb.tournament.dto.RankingEntryDto;
import com.polimi.ckb.tournament.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 *
 */
@RestController
@RequiredArgsConstructor
@RequestMapping("/tournament/ranking")
public class TournamentRankingController {

    private final RankingService rankingService;

    /**
     * Retrieves the ranking of a tournament based on the provided parameters.
     *
     * @param tournamentId The ID of the tournament.
     * @param minIndex The minimum index for the ranking.
     * @param maxIndex The maximum index for the ranking.
     * @return The ranking list as a ResponseEntity. If successful, the ranking list is returned as the response body.
     *         If an error occurs, an error message is returned as the response body along with an internal server error status.
     *
     * Example request: GET /tournament/ranking?tournamentId=1&minIndex=0&maxIndex=10
     */
    @GetMapping("/tournamentRanking")
    public ResponseEntity<Object> getTournamentRanking(
            @RequestParam("tournamentId") long tournamentId,
            @RequestParam("minIndex") int minIndex,
            @RequestParam("maxIndex") int maxIndex) {
        try {
            List<RankingEntryDto> ranking = rankingService.getTournamentRanking(tournamentId, minIndex, maxIndex);
            return ResponseEntity.ok(ranking);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ErrorResponse("Internal server error: " + e.getMessage()));
        }
    }

    /**
     * Retrieves the full tournament ranking based on the provided tournament ID.
     * Meaning there is no limit to the number of entries returned.
     *
     * @param tournamentId The ID of the tournament to retrieve the ranking for.
     * @return The full tournament ranking as a ResponseEntity. If successful, the ranking list is returned as the response body.
     *         If an error occurs, an error message is returned as the response body along with an internal server error status.
     */
    @GetMapping("/{tournamentId}")
    public ResponseEntity<Object> getFullTournamentRanking(@PathVariable Long tournamentId) {
        try {
            List<RankingEntryDto> ranking = rankingService.getTournamentRanking(tournamentId, null, null);
            return ResponseEntity.ok(ranking);
        } catch (Exception e) {
            return ResponseEntity.internalServerError().body(new ErrorResponse("Internal server error: " + e.getMessage()));
        }
    }

}
