package com.polimi.ckb.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Tournament {
    @Id
    @Column(name = "tournament_id")
    private Long tournamentId;

    @ManyToOne
    @JoinColumn(name="creator_id")
    private Educator creator;

    @ManyToMany
    @JoinTable(
            name = "tournament_participants",
            joinColumns = @JoinColumn(name = "tournament_id"),
            inverseJoinColumns = @JoinColumn(name = "student_id")
    )
    private Set<Student> students = new HashSet<>();

    @OneToMany(mappedBy = "tournament")
    private Set<Battle> battles = new HashSet<>();
}