package com.polimi.ckb.tournament.entity;

import jakarta.persistence.*;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

/**
 * Entity class for the Educator table.
 * Contains all the ids of the educators that are registered to the system.
 * It is updated by a kafka consumer.
 */
@Entity
@Data
@NoArgsConstructor
@Builder
@Table(name = "educator")
public class Educator {

    @Builder
    public Educator(Long educatorId, List<Tournament> tournaments, List<Tournament> organizedTournaments) {
        this.educatorId = educatorId;
        this.tournaments = tournaments != null ? tournaments : new ArrayList<>();
        this.organizedTournaments = organizedTournaments != null ? organizedTournaments : new ArrayList<>();
    }

    @Id
    @Column(name = "educator_id")
    private Long educatorId;

    @ManyToMany(mappedBy = "organizers")
    private List<Tournament> tournaments;

    @ManyToMany(mappedBy = "organizers")
    private List<Tournament> organizedTournaments;

}