package com.polimi.ckb.tournament.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.polimi.ckb.tournament.entity.Student;
import com.polimi.ckb.tournament.utility.UserType;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * DTO for {@link Student}
 */
@Getter
@Builder
@NoArgsConstructor
@Setter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewUserDto implements Serializable{
    @NotBlank(message = "userId cannot be blank")
    private Long userId;

    private UserType type;
}