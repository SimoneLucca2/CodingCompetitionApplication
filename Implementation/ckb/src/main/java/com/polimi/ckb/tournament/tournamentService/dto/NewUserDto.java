package com.polimi.ckb.tournament.tournamentService.dto;

import com.polimi.ckb.tournament.tournamentService.entity.Student;
import com.polimi.ckb.user.utility.UserType;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.ToString;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Positive;
import java.io.Serializable;

/**
 * DTO for {@link Student}
 */
@NoArgsConstructor
@Setter
@Getter
@ToString
public class NewUserDto implements Serializable {
    @NotNull
    @Positive
    Long userId;

    @NotNull
    UserType type;
}