package com.polimi.ckb.battleService.entity;

import com.polimi.ckb.battleService.config.BattleStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Getter
@Setter
@ToString
//@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Battle {

    @Builder
    public Battle(Long battleId, String name, String description, Educator creator, String registrationDeadline, String submissionDeadline, String repoLink, Long tournamentId, BattleStatus status, List<StudentGroup> studentGroups, int maxGroupSize, int minGroupSize) {
        this.battleId = battleId;
        this.name = name;
        this.description = description;
        this.creator = creator;
        this.registrationDeadline = registrationDeadline;
        this.submissionDeadline = submissionDeadline;
        this.repoLink = repoLink;
        this.tournamentId = tournamentId;
        this.status = status;
        this.studentGroups = studentGroups == null ? new ArrayList<>() : studentGroups;
        this.maxGroupSize = maxGroupSize;
        this.minGroupSize = minGroupSize;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "battle_id")
    private Long battleId;

    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private Educator creator;

    private String registrationDeadline;

    private String submissionDeadline;

    private String repoLink;

    private Long tournamentId;

    private BattleStatus status;

    @OneToMany(mappedBy = "groupId")
    //@ToString.Exclude
    private List<StudentGroup> studentGroups = new ArrayList<>();

    private int maxGroupSize;

    private int minGroupSize;
}
