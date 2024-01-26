package com.polimi.ckb.timeServer.entity;

import com.polimi.ckb.timeServer.config.TournamentStatus;
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

}