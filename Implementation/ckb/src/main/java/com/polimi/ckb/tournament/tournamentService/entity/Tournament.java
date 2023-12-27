package com.polimi.ckb.tournament.tournamentService.entity;

import lombok.Builder;
import lombok.Getter;
import java.time.LocalDate;

@Getter
@Builder
public class Tournament {

    public enum TournamentStatus { PREPARE, ACTIVE, CLOSING, CLOSED }

    private Long id;
    private String name;
    private String creator;
    private LocalDate registrationDeadline;
    private TournamentStatus status;
}
