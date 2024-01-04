package com.polimi.ckb.tournament.dto;

import com.polimi.ckb.tournament.entity.Tournament;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * DTO for {@link Tournament}
 */

@NoArgsConstructor
@Setter
@Getter
public class GetTournamentRankingDto implements Serializable {

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
