package com.polimi.ckb.user.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@DiscriminatorValue("EDUCATOR")
public class Educator extends User {

    @OneToMany(mappedBy = "battleCreator")
    private Set<Battle> battles = new HashSet<>();

    @OneToMany(mappedBy = "creator")
    private List<Tournament> tournaments;

}
