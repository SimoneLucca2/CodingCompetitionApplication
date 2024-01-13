package com.polimi.ckb.battleService.service.impl;

import com.polimi.ckb.battleService.config.BattleStatus;
import com.polimi.ckb.battleService.dto.StudentInvitesToGroupDto;
import com.polimi.ckb.battleService.dto.StudentJoinsGroupDto;
import com.polimi.ckb.battleService.dto.StudentLeavesGroupDto;
import com.polimi.ckb.battleService.entity.Battle;
import com.polimi.ckb.battleService.entity.Student;
import com.polimi.ckb.battleService.entity.StudentGroup;
import com.polimi.ckb.battleService.exception.*;
import com.polimi.ckb.battleService.repository.BattleRepository;
import com.polimi.ckb.battleService.repository.GroupRepository;
import com.polimi.ckb.battleService.repository.StudentRepository;
import com.polimi.ckb.battleService.service.GroupService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GroupServiceImpl implements GroupService {
    private final GroupRepository groupRepository;
    private final BattleRepository battleRepository;
    private final StudentRepository studentRepository;


    @Override
    @Transactional
    public void inviteStudentToGroup(@Valid StudentInvitesToGroupDto studentInvitesToGroupDto) {
        StudentGroup group = groupRepository.findById(studentInvitesToGroupDto.getGroupId()).orElse(null);
        //group is never null thanks to the @Valid annotation
        assert group != null;
        Battle battle = battleRepository.findById(group.getBattle().getBattleId()).orElse(null);
        //battle is never null otherwise the group would not exist
        assert battle != null;
        if(!battle.getStatus().equals(BattleStatus.PRE_BATTLE)){
            throw new BattleStateTooAdvancedException();
        }
    }

    @Override
    @Transactional
    public StudentGroup joinGroup(@Valid StudentJoinsGroupDto studentJoinsGroupDto) {
        StudentGroup group = groupRepository.findById(studentJoinsGroupDto.getGroupId()).orElse(null);
        //group is never null thanks to the @Valid annotation
        assert group != null;
        Battle battle = battleRepository.findById(group.getBattle().getBattleId()).orElse(null);
        //battle is never null otherwise the group would not exist
        assert battle != null;
        if(!battle.getStatus().equals(BattleStatus.PRE_BATTLE)){
            throw new BattleStateTooAdvancedException();
        }
        if(group.getStudents().size() == battle.getMaxGroupSize()){
            throw new GroupIsFullException();
        }


        //student cannot join the group if he is not registered to the battle
        Student student = studentRepository.findById(studentJoinsGroupDto.getStudentId()).orElseThrow(StudentNotRegisteredInBattleException::new);

        //need to find the old group
        final List<StudentGroup> groupsRegisteredToBattle = groupRepository.findByBattle(battle);
        StudentGroup groupToDelete = null;
        for(StudentGroup groupRegisteredToBattle : groupsRegisteredToBattle){
            if(groupRegisteredToBattle.getStudents().contains(student)){
                groupToDelete = groupRegisteredToBattle;
                break;
            }
        }

        assert groupToDelete != null;
        //if groupToDelete size is 1 then the group must be deleted otherwise the student must leave it before joining the new one
        if(groupToDelete.getStudents().size() == 1){
            groupRepository.delete(groupToDelete);
            battle.getStudentGroups().remove(groupToDelete);
            battleRepository.save(battle);
        } else {
            throw new StudentAlreadyInAnotherGroupException();
        }

        group.getStudents().add(student);
        student.getStudentGroups().remove(groupToDelete);
        student.getStudentGroups().add(group);

        groupRepository.save(group);
        studentRepository.save(student);

        return group;
    }

    @Override
    @Transactional
    public StudentGroup leaveGroup(@Valid StudentLeavesGroupDto studentLeavesGroupDto) {
        StudentGroup group = groupRepository.findById(studentLeavesGroupDto.getGroupId()).orElse(null);
        //group is never null thanks to the @Valid annotation
        assert group != null;
        Battle battle = battleRepository.findById(group.getBattle().getBattleId()).orElse(null);
        //battle is never null otherwise the group would not exist
        assert battle != null;
        if(!((battle.getStatus().equals(BattleStatus.PRE_BATTLE)) || (battle.getStatus().equals(BattleStatus.BATTLE)))){
            throw new BattleStateTooAdvancedException();
        }

        Student student = studentRepository.findById(studentLeavesGroupDto.getStudentId()).orElse(null);
        //student is never null thanks to the @Valid annotation
        assert student != null;
        if(!group.getStudents().contains(student)){
            throw new StudentNotMemberOfGroupException();
        }

        //at this point the student can leave the group
        student.getStudentGroups().remove(group);
        group.getStudents().remove(student);
        studentRepository.save(student);

        //if battle-status is BATTLE, must check the group constraints, if it is PRE_BATTLE, operation is complete
        if(battle.getStatus().equals(BattleStatus.PRE_BATTLE)){
            groupRepository.save(group);
            group = null;
        }else if(battle.getStatus().equals(BattleStatus.BATTLE)){
            if(group.getStudents().size() < battle.getMinGroupSize()){
                //every other student in the group must be removed both from the group (see for loop) and from the battle (the caller will do it)
                for(Student studentInGroup : group.getStudents()){
                    studentInGroup.getStudentGroups().remove(group);
                    studentRepository.save(studentInGroup);
                }
                groupRepository.delete(group);
                battle.getStudentGroups().remove(group);
                battleRepository.save(battle);
            } else {
                group = null;
            }
        }

        //if null, group still exists, otherwise it has been deleted but caller needs their ids to tell kafka
        return group;
    }
}
