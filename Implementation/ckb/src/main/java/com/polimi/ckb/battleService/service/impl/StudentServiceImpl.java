package com.polimi.ckb.battleService.service.impl;

import com.polimi.ckb.battleService.dto.NewUserDto;
import com.polimi.ckb.battleService.dto.StudentJoinBattleDto;
import com.polimi.ckb.battleService.dto.StudentLeaveBattleDto;
import com.polimi.ckb.battleService.entity.Battle;
import com.polimi.ckb.battleService.entity.StudentGroup;
import com.polimi.ckb.battleService.entity.Student;
import com.polimi.ckb.battleService.exception.*;
import com.polimi.ckb.battleService.repository.BattleRepository;
import com.polimi.ckb.battleService.repository.GroupRepository;
import com.polimi.ckb.battleService.repository.StudentRepository;
import com.polimi.ckb.battleService.service.StudentService;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {
    private final StudentRepository studentRepository;

    @Override
    @Transactional
    public void addNewUser(NewUserDto newUserDto) {
        studentRepository.save(convertToEntity(newUserDto));
    }

    private Student convertToEntity(NewUserDto newUserDto) {
        return Student.builder()
                .studentId(newUserDto.getUserId()).build();
    }
}
