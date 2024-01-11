package com.polimi.ckb.timeServer.dto;

import com.polimi.ckb.tournament.config.TournamentStatus;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Builder
@NoArgsConstructor
@Setter
@AllArgsConstructor
public class ChangeTournamentStatusDto implements Serializable {

    @NotBlank(message = "EducatorId cannot be null")
    private Long educatorId; //id of the educator who is requesting the change

    @NotBlank(message = "TournamentId cannot be null")
    private Long tournamentId;

    @NotBlank(message = "Status cannot be null")
    private TournamentStatus status;

}