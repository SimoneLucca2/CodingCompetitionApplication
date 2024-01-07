package com.polimi.ckb.battleService.entity;

import com.polimi.ckb.battleService.config.BattleStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Getter
@Setter
@ToString
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class Battle {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "battle_id")
    private Long battleId;

    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private Educator creatorId;

    private String registrationDeadline;

    private String submissionDeadline;

    private String repoLink;

    private Long tournamentId;

    private BattleStatus status;

    @OneToMany(mappedBy = "groupId")
    //@ToString.Exclude
    private List<StudentGroup> studentGroups;

    private int maxGroupSize;

    private int minGroupSize;
}
