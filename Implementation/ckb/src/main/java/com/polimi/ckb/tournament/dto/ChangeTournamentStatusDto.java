package com.polimi.ckb.tournament.dto;

import com.polimi.ckb.tournament.config.TournamentStatus;
import com.polimi.ckb.tournament.entity.Tournament;
import lombok.*;

import javax.validation.constraints.NotBlank;

/**
 * DTO for {@link Tournament}
 */

@Getter
@Builder
@NoArgsConstructor
@Setter
@AllArgsConstructor
public class ChangeTournamentStatusDto {

    @NotBlank(message = "EducatorId cannot be null")
    private Long educatorId; //id of the educator who is requesting the change

    @NotBlank(message = "TournamentId cannot be null")
    private Long tournamentId;

    @NotBlank(message = "Status cannot be null")
    private TournamentStatus status;

}