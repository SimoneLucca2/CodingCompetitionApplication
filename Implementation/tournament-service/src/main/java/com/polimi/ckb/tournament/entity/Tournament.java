package com.polimi.ckb.tournament.entity;

import com.polimi.ckb.tournament.config.TournamentStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
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

    @Column(name = "description")
    private String description;

    @Column(name = "creator_id")
    private Long creatorId;

    @Column(name = "registration_deadline")
    private String registrationDeadline;

    @Enumerated(EnumType.STRING)
    private TournamentStatus status;

    @ManyToMany(fetch = FetchType.EAGER)
    @JoinTable(
            name = "tournament_organizers",
            joinColumns = @JoinColumn(name = "tournament_id"),
            inverseJoinColumns = @JoinColumn(name = "educator_id")
    )
    @ToString.Exclude
    private List<Educator> organizers = new ArrayList<>();

    @OneToMany(mappedBy = "tournament")
    @ToString.Exclude
    private List<Score> scores = new ArrayList<>();

    @ManyToMany(mappedBy = "tournaments")
    @ToString.Exclude
    private List<Student> participants = new ArrayList<>();

}