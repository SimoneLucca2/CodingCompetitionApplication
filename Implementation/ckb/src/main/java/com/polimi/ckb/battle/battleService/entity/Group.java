package com.polimi.ckb.battle.battleService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class Group {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long groupId;

    @ManyToMany(mappedBy = "groups")
    private List<Student> students;

    @ManyToOne
    @JoinColumn(name = "battle_id")
    private Battle battle;
}
