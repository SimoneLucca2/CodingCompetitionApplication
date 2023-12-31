package com.polimi.ckb.battle.battleService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.LinkedHashSet;
import java.util.List;
import java.util.Set;

@Entity
@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class StudentEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long studentId;

    @OneToMany(mappedBy = "groupId")
    private List<GroupEntity> groups;

    @OneToMany(mappedBy = "studentId")
    private List<ScoreEntity> scores;

    @OneToMany(mappedBy = "students", orphanRemoval = true)
    private Set<ScoreEntity> scoreEntities = new LinkedHashSet<>();

}
