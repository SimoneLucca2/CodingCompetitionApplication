package com.polimi.ckb.user.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Builder
@NoArgsConstructor
@Setter
@AllArgsConstructor
public class StudentQuitsTournamentDto implements Serializable {
    @NotBlank(message = "student id cannot be null")
    private Long studentId;
    @NotBlank(message = "tournament id cannot be null")
    private Long tournamentId;
}
