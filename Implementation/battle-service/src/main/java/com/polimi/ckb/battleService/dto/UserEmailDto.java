package com.polimi.ckb.battleService.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.*;

import javax.validation.constraints.NotBlank;

@Getter
public class UserEmailDto {
    private String email;
}
