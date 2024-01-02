package com.polimi.ckb.tournament.tournamentService.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Score {

    @EmbeddedId
    private ScoreId id;

    @ManyToOne
    @MapsId("studentId") // This maps studentId in ScoreId
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @MapsId("tournamentId") // This maps tournamentId in ScoreId
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    private Integer scoreValue;
}