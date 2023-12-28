package com.polimi.ckb.tournament.tournamentService.entity;

import com.polimi.ckb.tournament.tournamentService.config.TournamentStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Tournament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tournament_id;

    private String name;
    private String creator_id;
    private String registrationDeadline;

    @Enumerated(EnumType.STRING)
    private TournamentStatus status;

    @ManyToMany
    @JoinTable(
            name = "tournament_organizers",
            joinColumns = @JoinColumn(name = "tournament_id"),
            inverseJoinColumns = @JoinColumn(name = "educator_id")
    )
    private List<Educator> organizers;

    @ManyToMany
    @JoinTable(
            name = "tournament_badges",
            joinColumns = @JoinColumn(name = "tournament_id"),
            inverseJoinColumns = @JoinColumn(name = "badge_id")
    )
    private List<Badge> badges;


    @OneToMany(mappedBy = "tournament")
    private List<Score> scores;

}