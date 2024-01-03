package com.polimi.ckb.tournament.dto;

import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@NoArgsConstructor
@Setter
@Getter
public class GetTournamentRankingDto {

    @NotBlank(message = "TournamentId cannot be blank")
    Long tournamentId;

    /**
     * get ranking starting from this index
     */
    Integer firstIndex;

    /**
     * get ranking up to this index
     */
    Integer lastIndex;

}
