package com.polimi.ckb.tournament.tournamentService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

/**
 * Entity class for the Educator table.
 * Contains all the ids of the educators that are registered to the system.
 * It is updated by a kafka consumer.
 */
@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Table(name = "educator")
public class Educator {
    @Id
    @Column(name = "educator_id")
    private Long educatorId;

    @ManyToMany(mappedBy = "organizers")
    private List<Tournament> tournaments;

    @ManyToMany(mappedBy = "organizers")
    private List<Tournament> organizedTournaments;

}