package com.polimi.ckb.apiGateway.dto;

import com.polimi.ckb.apiGateway.utility.UserType;
import com.polimi.ckb.apiGateway.utility.annotations.EmailNotInUse;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {

    @EmailNotInUse
    private String email;
    private String password;
    private String name;
    private String surname;
    private String nickname;
    private UserType accountType;

}