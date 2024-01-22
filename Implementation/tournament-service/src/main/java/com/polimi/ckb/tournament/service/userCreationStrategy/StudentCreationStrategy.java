package com.polimi.ckb.tournament.service.userCreationStrategy;

import com.polimi.ckb.tournament.dto.NewUserDto;
import com.polimi.ckb.tournament.service.StudentService;
import com.polimi.ckb.tournament.utility.UserType;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentCreationStrategy implements UserCreationStrategy {
    private final StudentService studentService;

    @Override
    public void addNewUser(NewUserDto userDto) {
        studentService.addNewUser(userDto);
    }

    @Override
    public UserType getUserType() {
        return UserType.STUDENT;
    }
}