package com.polimi.ckb.user.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.OneToOne;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
@DiscriminatorValue("EDUCATOR")
public class Educator extends User {

    @OneToMany(mappedBy = "battleCreator")
    private Set<Battle> battles = new HashSet<>();

    @OneToOne(mappedBy = "tournamentCreator")
    private Educator tournamentCreator;

}
