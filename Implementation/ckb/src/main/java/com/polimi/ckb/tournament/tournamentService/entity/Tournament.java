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
    @Column(name = "tournament_id")
    private Long tournamentId;

    @Column(name = "name")
    private String name;

    @Column(name = "creator_id")
    private Long creatorId;

    @Column(name = "registration_deadline")
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

    @OneToMany(mappedBy = "tournament")
    @ToString.Exclude
    private List<Score> scores;

}