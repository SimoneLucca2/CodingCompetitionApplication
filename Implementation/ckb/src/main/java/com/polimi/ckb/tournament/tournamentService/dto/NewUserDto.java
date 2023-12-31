package com.polimi.ckb.tournament.tournamentService.dto;

import com.polimi.ckb.tournament.tournamentService.entity.Student;
import com.polimi.ckb.user.utility.UserType;
import lombok.Value;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

/**
 * DTO for {@link Student}
 */
@Value
public class NewUserDto implements Serializable {
    @NotNull
    @Positive
    Long userId;

    @NotNull
    UserType type;
}