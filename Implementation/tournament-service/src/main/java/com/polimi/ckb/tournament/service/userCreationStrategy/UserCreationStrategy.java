package com.polimi.ckb.tournament.service.userCreationStrategy;

import com.polimi.ckb.tournament.dto.NewUserDto;
import com.polimi.ckb.tournament.utility.UserType;

public interface UserCreationStrategy {
    void addNewUser(NewUserDto userDto);
    UserType getUserType();

}
