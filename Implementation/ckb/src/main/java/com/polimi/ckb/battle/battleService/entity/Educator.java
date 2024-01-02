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
public class Educator {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "educator_id")
    private Long educatorId;

    @OneToMany(mappedBy = "battleId")
    private List<Battle> battles;
}
