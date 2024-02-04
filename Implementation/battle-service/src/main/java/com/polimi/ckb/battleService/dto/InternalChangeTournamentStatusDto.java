package com.polimi.ckb.battleService.dto;

import com.polimi.ckb.battleService.config.TournamentStatus;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;


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