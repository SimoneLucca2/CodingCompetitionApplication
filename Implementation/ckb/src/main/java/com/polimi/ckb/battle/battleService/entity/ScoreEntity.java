package com.polimi.ckb.battle.battleService.entity;

import jakarta.persistence.*;
import lombok.Data;

@Entity
@Data
public class ScoreEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer scoreValue;

    @ManyToOne
    @JoinColumn(name = "studentId")
    private StudentEntity studentEntity;

    @ManyToOne
    @JoinColumn(name = "battleId")
    private BattleEntity battleEntity;

}
