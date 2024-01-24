package com.polimi.ckb.battleService.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.polimi.ckb.battleService.utility.UserType;
import jakarta.persistence.Column;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Builder
@NoArgsConstructor
@Setter
@AllArgsConstructor
@JsonIgnoreProperties(ignoreUnknown = true)
public class NewUserDto implements Serializable {

    @NotBlank(message = "userId cannot be blank")
    private Long userId;

    private String email;

    private String name;

    private String surname;

    private String nickname;

    @NotBlank(message = "type cannot be blank")
    @Column(name = "type")
    private UserType type;

}
