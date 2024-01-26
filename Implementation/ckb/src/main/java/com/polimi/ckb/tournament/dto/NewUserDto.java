package com.polimi.ckb.tournament.dto;

import com.polimi.ckb.tournament.entity.Student;
import com.polimi.ckb.user.utility.UserType;
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
public class NewUserDto implements Serializable{
    @NotBlank(message = "userId cannot be blank")
    private Long userId;

    private String name;
    private String email;
    private UserType type;
}