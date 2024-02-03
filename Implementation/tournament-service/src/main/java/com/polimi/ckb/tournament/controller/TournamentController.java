package com.polimi.ckb.tournament.controller;

import com.polimi.ckb.tournament.dto.CreateTournamentDto;
import com.polimi.ckb.tournament.dto.ErrorResponse;
import com.polimi.ckb.tournament.dto.TournamentDto;
import com.polimi.ckb.tournament.entity.Tournament;
import com.polimi.ckb.tournament.repository.TournamentRepository;
import com.polimi.ckb.tournament.service.TournamentService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping("/tournament")
public class TournamentController {
    private final TournamentService tournamentService;

    private static final Logger log = LoggerFactory.getLogger(TournamentController.class);
    private final TournamentRepository tournamentRepository;

    @PostMapping
    public ResponseEntity<Object> createTournament(@Valid @RequestBody CreateTournamentDto msg) {
        try {
            log.info("Creating tournament with message: {}", msg);
            Tournament createdTournament = tournamentService.createTournament(msg);
            log.info("Tournament created successfully");
            return ResponseEntity.ok(TournamentDto.fromEntity(createdTournament));
        } catch (Exception e) {
            log.error("Internal server error: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(new ErrorResponse("Internal server error: " + e.getMessage()));
        }
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllTournaments() {
        try {
            log.info("Getting all tournaments");
            List<Tournament> tournaments = tournamentService.getAllTournaments();
            log.info("Tournaments retrieved successfully");
            return ResponseEntity.ok(tournaments.stream().map(TournamentDto::fromEntity));
        } catch (Exception e) {
            log.error("Internal server error: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(new ErrorResponse("Internal server error: " + e.getMessage()));
        }
    }

    @GetMapping("/enrolled/{studentId}")
    public ResponseEntity<Object> getTournamentsOfStudent(@PathVariable(name = "studentId") Long studentId) {
        try {
            log.info("Getting all tournaments");
            List<Tournament> tournaments = tournamentRepository.getTournamentsOfStudent(studentId);
            log.info("Tournaments retrieved successfully");
            return ResponseEntity.ok(tournaments.stream().map(TournamentDto::fromEntity));
        } catch (Exception e) {
            log.error("Internal server error: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(new ErrorResponse("Internal server error: " + e.getMessage()));
        }
    }

    @GetMapping("/notEnrolled/{studentId}")
    public ResponseEntity<Object> getTournamentsWithoutStudent(@PathVariable(name = "studentId") Long studentId) {
        try {
            log.info("Getting tournaments without student: {}", studentId);
            List<Tournament> tournaments = tournamentRepository.getTournamentsWithoutStudent(studentId);
            log.info("Tournaments retrieved successfully");
            return ResponseEntity.ok(tournaments.stream().map(TournamentDto::fromEntity));
        } catch (Exception e) {
            log.error("Internal server error: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(new ErrorResponse("Internal server error: " + e.getMessage()));
        }
    }

    @GetMapping("/administrated/{educatorId}")
    public ResponseEntity<Object> getTournamentsAdministratedBy(@PathVariable(name = "educatorId") Long educatorId) {
        try {
            log.info("Getting tournaments administrated by educator: {}", educatorId);
            List<Tournament> tournaments = tournamentRepository.getTournamentsAdministratedBy(educatorId);
            log.info("Tournaments retrieved successfully");
            return ResponseEntity.ok(tournaments.stream().map(TournamentDto::fromEntity));
        } catch (Exception e) {
            log.error("Internal server error: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(new ErrorResponse("Internal server error: " + e.getMessage()));
        }
    }

    @GetMapping("/notAdministrated/{educatorId}")
    public ResponseEntity<Object> getTournamentsNotAdministratedBy(@PathVariable(name = "educatorId") Long educatorId) {
        try {
            log.info("Getting tournaments administrated by educator: {}", educatorId);
            List<Tournament> tournaments = tournamentRepository.getTournamentsNotAdministratedBy(educatorId);
            log.info("Tournaments retrieved successfully");
            return ResponseEntity.ok(tournaments.stream().map(TournamentDto::fromEntity));
        } catch (Exception e) {
            log.error("Internal server error: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(new ErrorResponse("Internal server error: " + e.getMessage()));
        }
    }

    @GetMapping("/{tournamentId}")
    public ResponseEntity<Object> getTournament(@PathVariable(name = "tournamentId") Long tournamentId) {
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

    @GetMapping("/preparation")
    public ResponseEntity<Object> getPreparationTournaments() {
        try {
            log.info("Getting tournaments in preparation");
            List<Tournament> tournaments = tournamentService.getPreparationTournaments();
            log.info("Tournaments retrieved successfully");
            return ResponseEntity.ok(tournaments.stream().map(TournamentDto::fromEntity));
        } catch (Exception e) {
            log.error("Internal server error: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(new ErrorResponse("Internal server error: " + e.getMessage()));
        }
    }

    @GetMapping("/active")
    public ResponseEntity<Object> getActiveTournaments() {
        try {
            log.info("Getting tournaments in active state");
            List<Tournament> tournaments = tournamentService.getActiveTournaments();
            log.info("Tournaments retrieved successfully");
            return ResponseEntity.ok(tournaments.stream().map(TournamentDto::fromEntity));
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
