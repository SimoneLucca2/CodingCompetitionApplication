package com.polimi.ckb.tournament.tournamentService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
public class Tournament {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;
    private String creator;
    private String registrationDeadline;
    private String status;

    @ManyToMany(mappedBy = "tournaments")
    private List<Educator> educators;
}