package com.polimi.ckb.apiGateway.dto;

import com.polimi.ckb.apiGateway.entity.User;
import com.polimi.ckb.apiGateway.utility.UserType;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.Id;
import jakarta.validation.constraints.NotBlank;
import lombok.*;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class NewUserDto {

    @NotBlank(message = "userId cannot be blank")
    private Long userId;

    private String email;
    private String name;
    private String surname;
    private String nickname;
    private UserType type;

    public static NewUserDto buildFromUser(User user){
        return NewUserDto.builder()
                .userId(user.getUserId())
                .email(user.getEmail())
                .name(user.getName())
                .surname(user.getSurname())
                .nickname(user.getNickname())
                .type(user.getType())
                .build();
    }
}
