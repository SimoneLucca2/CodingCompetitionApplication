package com.polimi.ckb.tournament.service.userCreationStrategy;

import com.polimi.ckb.tournament.dto.NewUserDto;
import com.polimi.ckb.tournament.service.EducatorService;
import com.polimi.ckb.tournament.utility.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EducatorCreationStrategy implements UserCreationStrategy {
    private final EducatorService educatorService;

    @Override
    public void addNewUser(NewUserDto userDto) {
        educatorService.addNewUser(userDto);
    }

    @Override
    public UserType getUserType() {
        return UserType.EDUCATOR;
    }
}