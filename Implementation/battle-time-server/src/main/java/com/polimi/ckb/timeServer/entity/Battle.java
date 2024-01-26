package com.polimi.ckb.timeServer.entity;


import com.polimi.ckb.timeServer.config.BattleStatus;
import jakarta.persistence.*;
import lombok.Setter;

@Entity
public class Battle {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "battle_id")
    private Long battleId;

    private String name;

    private String description;

    private String registrationDeadline;

    private String submissionDeadline;

    private String repoLink;

    private Long tournamentId;

    @Setter
    private BattleStatus status;

    private int maxGroupSize;

    private int minGroupSize;
}
