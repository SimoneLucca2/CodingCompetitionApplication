package com.polimi.ckb.user.dto;

import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
@Builder
@NoArgsConstructor
@Setter
@AllArgsConstructor
public class GetUserDto {
    @NotBlank(message = "userId cannot be blank")
    private Long userId;
}
