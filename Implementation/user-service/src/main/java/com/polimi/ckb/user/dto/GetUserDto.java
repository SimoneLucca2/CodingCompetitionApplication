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
public class GetUserDto implements Serializable {
    @NotBlank(message = "userId cannot be blank")
    private Long userId;
    @NotBlank(message = "user type cannot be blank")
    private UserType userType;
}
