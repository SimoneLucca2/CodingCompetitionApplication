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
public class Student {
    @Builder
    public Student(Long studentId, List<StudentGroup> studentGroups) {
        this.studentId = studentId;
        this.studentGroups = studentGroups == null ? new ArrayList<>() : studentGroups;
    }

    @Id
    @Column(name = "student_id")
    private Long studentId;

    @ManyToMany
    @JoinTable(
            name = "student_group_join",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "group_id"))
    @ToString.Exclude
    private List<StudentGroup> studentGroups;
}
