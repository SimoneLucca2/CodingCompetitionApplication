package com.polimi.ckb.battleService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Educator {
    @Builder
    public Educator(Long educatorId) {
        this.educatorId = educatorId;
        //this.battles = battles == null ? new ArrayList<>() : battles;
    }

    @Id
    @Column(name = "educator_id")
    private Long educatorId;

    @OneToMany(mappedBy = "battleId")
    private List<Battle> battles;
}
