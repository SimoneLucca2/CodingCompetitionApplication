package com.polimi.ckb.user.entity;

import com.polimi.ckb.user.utility.UserType;
import jakarta.persistence.*;
import lombok.*;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Entity
@Builder
public class User {

    @Id
    private Long userId;

    private String email;
    private String name;
    private String surname;
    private String nickname;
    private UserType type;

}

