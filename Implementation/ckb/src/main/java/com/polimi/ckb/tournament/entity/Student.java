package com.polimi.ckb.tournament.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class Student {
    @Id
    @Column(name = "student_id")
    private Long studentId;

    @ManyToMany
    @JoinTable(
            name = "student_participate_tournament",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "tournament_id"))
    private List<Tournament> tournaments = new ArrayList<>();

    @OneToMany(mappedBy = "student")
    private List<Score> scores = new ArrayList<>();
}