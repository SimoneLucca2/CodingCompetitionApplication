package com.polimi.ckb.apiGateway.entity;

import com.polimi.ckb.apiGateway.utility.UserType;
import jakarta.persistence.*;
import lombok.*;

import java.util.Collection;
import java.util.List;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "user")
public class User {

    @Id
    @GeneratedValue
    private Long userId;

    private String email;
    @Getter
    private String password;
    private String name;
    private String surname;
    private String nickname;
    private UserType type;

}
