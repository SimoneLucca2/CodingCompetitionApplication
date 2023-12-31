package com.polimi.ckb.battle.battleService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Data
public class ScoreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer scoreValue;

    @ManyToOne
    @JoinColumn(name = "studentId")
    private StudentEntity students;

    @ManyToOne
    @JoinColumn(name = "battleId")
    private BattleEntity battles;
}
