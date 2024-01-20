package com.polimi.ckb.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Entity
@NoArgsConstructor
@DiscriminatorValue("STUDENT")
@Data
@AllArgsConstructor
public class Student extends User {

    @ManyToMany
    @JoinTable(
            name = "battle_participants",
            joinColumns = @JoinColumn(name = "student_id"),
            inverseJoinColumns = @JoinColumn(name = "battle_id")
    )
    private Set<Battle> battles = new HashSet<>();

    @ManyToMany(mappedBy = "students")
    private List<Tournament> tournaments = new ArrayList<>();

}
