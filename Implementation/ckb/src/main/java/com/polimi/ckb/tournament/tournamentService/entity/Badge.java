package com.polimi.ckb.tournament.tournamentService.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.util.List;

@Entity
@Data
public class Badge {

    @Id
    private Long id;

    @ManyToMany(mappedBy = "badges")
    private List<Tournament> tournaments;

}
