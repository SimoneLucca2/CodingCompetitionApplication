package com.polimi.ckb.tournament.dto;

import com.polimi.ckb.tournament.config.TournamentStatus;
import com.polimi.ckb.tournament.entity.Tournament;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * DTO for {@link Tournament}
 */

@Getter
@Builder
@NoArgsConstructor
@Setter
@AllArgsConstructor
public class InternalChangeTournamentStatusDto implements Serializable {

    @NotBlank(message = "TournamentId cannot be null")
    private Long tournamentId;

    @NotBlank(message = "Status cannot be null")
    private TournamentStatus status;

}