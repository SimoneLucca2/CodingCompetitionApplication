package com.polimi.ckb.battle.battleService.entity;

import com.polimi.ckb.battle.battleService.config.BattleStatus;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class BattleEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long BattleId;

    private String name;

    private String description;

    @ManyToOne
    @JoinColumn(name = "educatorId")
    private EducatorEntity creatorId;

    private String registrationDeadline;

    private String submissionDeadline;

    private String repoLink;

    private Long tournamentID;

    private BattleStatus status;

    @OneToMany(mappedBy = "battles")
    private List<ScoreEntity> scores;

    @OneToMany(mappedBy = "groupId")
    private List<GroupEntity> groups;
}
