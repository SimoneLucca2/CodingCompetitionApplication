package com.polimi.ckb.battleService.service.impl;

import com.polimi.ckb.battleService.dto.StudentJoinBattleDto;
import com.polimi.ckb.battleService.dto.StudentLeaveBattleDto;
import com.polimi.ckb.battleService.entity.Battle;
import com.polimi.ckb.battleService.entity.Student;
import com.polimi.ckb.battleService.entity.StudentGroup;
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
    private final GroupRepository groupRepository;
    private final BattleRepository battleRepository;

    @Override
    @Transactional
    public StudentGroup joinBattle(@Valid StudentJoinBattleDto studentDto) {
        //TODO: check if student is already in the battle
        Battle battle = battleRepository.findById(studentDto.getBattleId())
                .orElseThrow(BattleDoesNotExistException::new);

        Student student = studentRepository.findById(studentDto.getStudentId())
                .orElseThrow(StudentDoesNotExistException::new);

        List<StudentGroup> registeredGroups = groupRepository.findByBattle(battle);
        for (StudentGroup group : registeredGroups) {
            if (group.getStudents().contains(student)) {
                throw new StudentAlreadyRegisteredToBattleException();
            }
        }
        //validator for status check so it should be ok

        StudentGroup newStudentGroup = groupRepository.save(StudentGroup.builder()
                .battle(battle)
                .score(0)
                .build());
        newStudentGroup.getStudents().add(student);
        battle.getStudentGroups().add(newStudentGroup);
        student.getStudentGroups().add(newStudentGroup);

        return newStudentGroup;
    }

    @Transactional
    @Override
    public void leaveBattle(StudentLeaveBattleDto studentDto) {
        Battle battle = battleRepository.findById(studentDto.getBattleId())
                .orElseThrow(BattleDoesNotExistException::new);

        Student student = studentRepository.findById(studentDto.getStudentId())
                .orElseThrow(StudentDoesNotExistException::new);

        List<StudentGroup> registeredGroups = groupRepository.findByBattle(battle);
        boolean check = false;
        for (StudentGroup group : registeredGroups) {
            if (group.getStudents().contains(student)) {
                check = true;
                break;
            }
        }
        if (!check) {
            throw new StudentNotRegisteredInBattleException();
        }

        //check battle's status
        switch (battle.getStatus()) {
            case PRE_BATTLE:
                break;

            case BATTLE:
                break;

            default:
                throw new BattleStateTooAdvancedException();
        }
    }
}
