package com.polimi.ckb.tournament.tournamentService.entity;

import com.polimi.ckb.tournament.tournamentService.config.TournamentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class Tournament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long tournamentId;

    private String name;
    private String creatorId;
    private String registrationDeadline;

    @Enumerated(EnumType.STRING)
    private TournamentStatus status;

    @ManyToMany
    @JoinTable(
            name = "tournament_organizers",
            joinColumns = @JoinColumn(name = "tournament_id"),
            inverseJoinColumns = @JoinColumn(name = "educator_id")
    )
    @ToString.Exclude
    private List<Educator> organizers;

    @ManyToMany
    @JoinTable(
            name = "tournament_badges",
            joinColumns = @JoinColumn(name = "tournament_id"),
            inverseJoinColumns = @JoinColumn(name = "badge_id")
    )
    @ToString.Exclude
    private List<Badge> badges;


    @OneToMany(mappedBy = "tournament")
    @ToString.Exclude
    private List<Score> scores;

}