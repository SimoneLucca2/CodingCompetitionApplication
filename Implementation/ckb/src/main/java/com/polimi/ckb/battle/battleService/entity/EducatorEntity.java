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

//TODO: delete this class
public class EducatorEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long educatorId;

    @ManyToMany(mappedBy = "battleId")
    private List<BattleEntity> battles;
}
