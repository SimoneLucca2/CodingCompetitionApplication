package com.polimi.ckb.user.entity;

import jakarta.persistence.DiscriminatorValue;
import jakarta.persistence.Entity;
import jakarta.persistence.ManyToMany;
import jakarta.persistence.OneToMany;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
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
    private List<Battle> battles = new ArrayList<>();

    @OneToMany(mappedBy = "creator")
    private List<Tournament> tournaments;

    @ManyToMany(mappedBy = "organizers")
    private Set<Tournament> tournamentsCollaborate = new HashSet<>();

}
