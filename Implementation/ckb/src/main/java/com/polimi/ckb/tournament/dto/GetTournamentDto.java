package com.polimi.ckb.tournament.dto;

import com.polimi.ckb.tournament.entity.Tournament;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * DTO for {@link Tournament}
 */


@NoArgsConstructor
@Setter
@Getter
@Builder
@AllArgsConstructor
public class GetTournamentDto implements Serializable {
    @NotBlank(message = "TournamentId cannot be null")
    private Long tournamentId;
}
