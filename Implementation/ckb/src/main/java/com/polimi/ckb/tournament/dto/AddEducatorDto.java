package com.polimi.ckb.tournament.dto;

import com.polimi.ckb.tournament.entity.Educator;
import com.polimi.ckb.tournament.utility.messageValidator.annotation.IsCreator;
import lombok.*;

import javax.validation.constraints.NotBlank;

/**
 * DTO for {@link Educator}
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