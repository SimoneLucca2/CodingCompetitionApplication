package com.polimi.ckb.tournament.entity;

import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

@Entity
@ToString
@Data
public class Score {

    @EmbeddedId
    private ScoreId id;

    @ManyToOne
    @MapsId("studentId") // maps studentId in ScoreId
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @MapsId("tournamentId") // maps tournamentId in ScoreId
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    private Integer scoreValue;
}