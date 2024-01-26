package com.polimi.ckb.tournament.entity;

import com.polimi.ckb.tournament.entity.compositeKeys.ScoreId;
import jakarta.persistence.*;
import jakarta.ws.rs.DefaultValue;
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

    @DefaultValue("0")
    private Integer scoreValue;
}