package com.polimi.ckb.tournament.tournamentService.entity;

import jakarta.persistence.*;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Student {
    @Id
    private Long student_id;

    @ManyToMany
    @JoinTable(
            name = "student_participate_tournament",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "tournament_id"))
    private List<Tournament> tournaments;

    @OneToMany(mappedBy = "student")
    private List<Score> scores;
}