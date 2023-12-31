package com.polimi.ckb.tournament.tournamentService.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class Score {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer scoreValue;

    @ManyToOne
    @JoinColumn(name = "student_id")
    private Student student;

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

}
