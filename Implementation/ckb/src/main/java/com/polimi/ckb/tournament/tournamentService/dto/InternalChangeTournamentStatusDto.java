package com.polimi.ckb.tournament.tournamentService.dto;

import com.polimi.ckb.tournament.tournamentService.config.TournamentStatus;
import lombok.*;

import javax.validation.constraints.NotBlank;

/**
 * DTO for {@link com.polimi.ckb.tournament.tournamentService.entity.Tournament}
 */

@Getter
@Builder
@NoArgsConstructor
@Setter
@AllArgsConstructor
public class InternalChangeTournamentStatusDto {

    @NotBlank(message = "TournamentId cannot be null")
    private Long tournamentId;

    @NotBlank(message = "Status cannot be null")
    private TournamentStatus status;

}