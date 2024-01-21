package com.polimi.ckb.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Builder
@NoArgsConstructor
@Setter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class StudentJoinTournamentDto implements Serializable {
    @NotBlank(message = "student id cannot be null")
    private Long studentId;
    @NotBlank(message = "tournament id cannot be null")
    private Long tournamentId;
}
