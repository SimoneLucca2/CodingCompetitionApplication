package com.polimi.ckb.tournament.tournamentService.dto;

import com.polimi.ckb.tournament.tournamentService.utility.messageValidator.annotation.IsCreator;
import lombok.*;

import javax.validation.constraints.NotBlank;

/**
 * DTO for {@link com.polimi.ckb.tournament.tournamentService.entity.Educator}
 */

@Getter
@NoArgsConstructor
@Setter
@Builder
@AllArgsConstructor
public class AddEducatorDto {

    @NotBlank(message = "RequesterId cannot be null")
    @IsCreator
    private Long requesterId;

    @NotBlank(message = "TournamentId cannot be null")
    private Long tournamentId;

    @NotBlank(message = "EducatorId cannot be null")
    private Long educatorId;
}