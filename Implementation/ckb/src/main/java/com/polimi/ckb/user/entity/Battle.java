package com.polimi.ckb.user.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Battle {
    @Id
    @Column(name = "battle_id")
    private Long battleId;

    @ManyToOne
    @JoinColumn(name = "tournament_id")
    private Tournament tournament;

    @ManyToMany(mappedBy = "battles")
    private Set<Student> participants = new HashSet<>();

    @ManyToOne
    @JoinColumn(name = "creator_id")
    private Educator battleCreator;
}

