package com.polimi.ckb.user.dto;

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
}
