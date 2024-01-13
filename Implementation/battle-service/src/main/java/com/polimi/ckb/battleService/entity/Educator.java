package com.polimi.ckb.battleService.entity;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Entity
@Data
@AllArgsConstructor
@NoArgsConstructor
@Builder

//TODO: delete this class
public class Educator {
    @Id
    @Column(name = "educator_id")
    private Long educatorId;

    @OneToMany(mappedBy = "battleId")
    private List<Battle> battles;
}
