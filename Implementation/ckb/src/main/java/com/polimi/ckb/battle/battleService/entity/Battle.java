package com.polimi.ckb.battle.battleService.entity;

import com.polimi.ckb.battle.battleService.config.BattleStatus;
import jakarta.persistence.*;
import lombok.*;

import java.util.List;

@Entity
@Data
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

    private Long tournamentID;

    private BattleStatus status;

    @OneToMany(mappedBy = "groupId")
    private List<Group> groups;

    private int maxGroupSize;

    private int minGroupSize;
}
