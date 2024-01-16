package com.polimi.ckb.user.dto;

import com.polimi.ckb.user.utility.UserType;
import jakarta.persistence.Column;
import lombok.*;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Getter
@Builder
@NoArgsConstructor
@Setter
@AllArgsConstructor
public class NewUserDto implements Serializable {

    @NotBlank(message = "email cannot be blank")
    private String email;

    private String name;

    private String surname;

    private String nickname;

    @NotBlank(message = "type cannot be blank")
    @Column(name = "type")
    private UserType type;

}
