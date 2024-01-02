package com.polimi.ckb.tournament.tournamentService.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.ManyToMany;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.Objects;

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
public class Educator {
    @Id
    @Column(name = "educator_id")
    private Long educatorId;

    @ManyToMany(mappedBy = "organizers")
    private List<Tournament> tournaments;

    @ManyToMany(mappedBy = "organizers")
    private List<Tournament> organizedTournaments;

    /**
     * Checks if the current Educator object is equal to the specified object.
     * Two Educator objects are considered equal if they have the same ID.
     *
     * @param obj the object to compare against
     * @return true if the objects are equal, false otherwise
     */
    @Override
    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || getClass() != obj.getClass()) {
            return false;
        }
        Educator other = (Educator) obj;
        return Objects.equals(educatorId, other.educatorId);
    }

    @Override
    public int hashCode() {
        return Objects.hash(educatorId);
    }
}