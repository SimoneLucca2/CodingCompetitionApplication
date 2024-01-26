package com.polimi.ckb.timeServer.dto;

<<<<<<<< HEAD:Implementation/time-server/src/main/java/com/polimi/ckb/timeServer/dto/ChangeTournamentStatusDto.java
import com.polimi.ckb.timeServer.config.TournamentStatus;
========
import com.polimi.ckb.tournament.config.TournamentStatus;
>>>>>>>> origin/tournament_testing:Implementation/ckb/src/main/java/com/polimi/ckb/timeServer/dto/ChangeTournamentStatusDto.java
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