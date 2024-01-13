package com.polimi.ckb.battleService.dto;

import com.polimi.ckb.battleService.entity.Student;
import com.polimi.ckb.battleService.utility.UserType;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

/**
 * DTO for {@link Student}
 */

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class NewUserDto implements Serializable {
    @NotBlank(message = "userId cannot be blank")
    private Long userId;

    private String name;
    private String email;
    private UserType type;
}
