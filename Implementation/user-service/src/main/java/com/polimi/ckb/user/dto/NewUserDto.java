package com.polimi.ckb.user.dto;

import com.polimi.ckb.user.utility.UserType;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Builder
@NoArgsConstructor
@Setter
@AllArgsConstructor
public class NewUserDto implements Serializable {
    @NotBlank(message = "userId cannot be blank")
    private Long userId;

    private String name;
    @NotBlank(message = "email cannot be blank")
    private String email;
    @NotBlank(message = "type cannot be blank")
    private UserType type;
}
