package com.polimi.ckb.battle.battleService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor

public class EducatorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long EducatorId;

    @ManyToMany(mappedBy = "organizers")
    private List<BattleEntity> battleEntities;

    @ManyToMany(mappedBy = "organizers")
    private List<BattleEntity> organizedBattleEntities;
}
