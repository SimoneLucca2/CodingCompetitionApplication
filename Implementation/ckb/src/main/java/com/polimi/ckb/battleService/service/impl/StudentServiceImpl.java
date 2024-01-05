package com.polimi.ckb.battleService.service.impl;

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
    private final GroupRepository groupRepository;
    private final BattleRepository battleRepository;

    @Override
    @Transactional
    public StudentGroup joinBattle(@Valid StudentJoinBattleDto studentDto) {
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

        StudentGroup newStudentGroup = StudentGroup.builder()
                .battle(battle)
                .score(0)
                .build();
        newStudentGroup.getStudents().add(student);
        battle.getStudentGroups().add(newStudentGroup);
        student.getStudentGroups().add(newStudentGroup);

        battleRepository.save(battle);
        studentRepository.save(student);
        return groupRepository.save(newStudentGroup);
    }

    @Transactional
    @Override
    public StudentGroup leaveBattle(StudentLeaveBattleDto studentDto) {
        Battle battle = battleRepository.findById(studentDto.getBattleId())
                .orElseThrow(BattleDoesNotExistException::new);

        Student student = studentRepository.findById(studentDto.getStudentId())
                .orElseThrow(StudentDoesNotExistException::new);

        List<StudentGroup> registeredGroups = groupRepository.findByBattle(battle);
        StudentGroup leavingStudentGroup = null;
        boolean check = false;
        for (StudentGroup group : registeredGroups) {
            if (group.getStudents().contains(student)) {
                check = true;
                leavingStudentGroup = group;
                break;
            }
        }
        if (!check) {
            throw new StudentNotRegisteredInBattleException();
        }

        leavingStudentGroup.getStudents().remove(student);
        student.getStudentGroups().remove(leavingStudentGroup);
        studentRepository.save(student);

        //check battle's status
        switch (battle.getStatus()) {
            case BATTLE:
                if(leavingStudentGroup.getStudents().size() < battle.getMinGroupSize()) {
                    for(Student leftStudent : leavingStudentGroup.getStudents()){
                        //leavingStudentGroup.getStudents().remove(leftStudent);
                        student.getStudentGroups().remove(leavingStudentGroup);
                        studentRepository.save(leftStudent);
                    }
                } else {
                    leavingStudentGroup = null;
                    break;
                }

                //continue: the next case has also to be executed in order to complete the operation
            case PRE_BATTLE:
                if(leavingStudentGroup.getStudents().isEmpty()){
                    battle.getStudentGroups().remove(leavingStudentGroup);
                    battleRepository.save(battle);
                    groupRepository.delete(leavingStudentGroup);
                } else {
                    leavingStudentGroup = null;
                }
                break;

            default:
                throw new BattleStateTooAdvancedException();
        }

        //if null, group still exists, otherwise it has been deleted but caller needs their ids to tell kafka
        return leavingStudentGroup;
    }
}
