package com.polimi.ckb.battleService.entity;

import jakarta.persistence.*;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@NoArgsConstructor
@Getter
@Setter
@ToString
@Builder
@Table(name = "student_group")
public class StudentGroup {
    @Builder
    public StudentGroup(Long groupId, List<Student> students, Battle battle, Integer score, String clonedRepositoryLink) {
        this.groupId = groupId;
        this.students = students == null ? new ArrayList<>() : students;
        this.battle = battle;
        this.score = score;
        this.clonedRepositoryLink = clonedRepositoryLink;
    }

    @Id
    @GeneratedValue
    @Column(name = "group_id")
    private Long groupId;

    @ManyToMany(mappedBy = "studentGroups")
    @ToString.Exclude
    private List<Student> students;

    @ManyToOne
    @JoinColumn(name = "battle_id")
    @ToString.Exclude
    private Battle battle;

    private Integer score;

    private String clonedRepositoryLink;
}
