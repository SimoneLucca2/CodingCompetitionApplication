package com.polimi.ckb.battleService.dto;

import com.polimi.ckb.battleService.config.TournamentStatus;
import com.polimi.ckb.battleService.entity.Educator;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TournamentDto {

    private Long tournamentId;
    private String name;
    private Long creatorId;
    private String registrationDeadline;
    private TournamentStatus status;
    private List<Long> organizerIds;
}