package com.polimi.ckb.battleService.service.impl;

import com.polimi.ckb.battleService.config.BattleStatus;
import com.polimi.ckb.battleService.dto.*;
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
import java.util.ArrayList;
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
        //only check if the battle is in PRE_BATTLE state
        StudentGroup group = groupRepository.findById(studentInvitesToGroupDto.getGroupId()).orElseThrow(GroupDoesNotExistsException::new);
        Battle battle = battleRepository.findById(group.getBattle().getBattleId()).orElseThrow(BattleDoesNotExistException::new);

        if(!battle.getStatus().equals(BattleStatus.PRE_BATTLE)){
            throw new BattleStateTooAdvancedException();
        }
    }

    @Override
    @Transactional
    public StudentGroup joinGroup(@Valid StudentJoinsGroupDto studentJoinsGroupDto) {
        //a group can be joined if it is not full and if the battle is in PRE_BATTLE state
        StudentGroup group = groupRepository.findById(studentJoinsGroupDto.getGroupId()).orElseThrow(GroupDoesNotExistsException::new);

        Battle battle = battleRepository.findById(group.getBattle().getBattleId()).orElseThrow(BattleDoesNotExistException::new);

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

        //if no group is found, the student is not registered to the battle
        if(groupToDelete == null){
            throw new StudentNotRegisteredInBattleException();
        }

        if(groupToDelete.getStudents().size() == 1){
            groupRepository.delete(groupToDelete);
            battle.getStudentGroups().remove(groupToDelete);
            battleRepository.save(battle);
        } else {
            groupToDelete.getStudents().remove(student);
            groupRepository.save(groupToDelete);
        }

        //apply and save changes in the db
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
        StudentGroup group = groupRepository.findById(studentLeavesGroupDto.getGroupId()).orElseThrow(GroupDoesNotExistsException::new);

        Battle battle = battleRepository.findById(group.getBattle().getBattleId()).orElseThrow(BattleDoesNotExistException::new);

        //student can leave group only if the battle is in PRE_BATTLE or BATTLE state
        if(!((battle.getStatus().equals(BattleStatus.PRE_BATTLE)) || (battle.getStatus().equals(BattleStatus.BATTLE)))){
            throw new BattleStateTooAdvancedException();
        }

        Student student = studentRepository.findById(studentLeavesGroupDto.getStudentId()).orElseThrow(StudentDoesNotExistException::new);

        //the student must be member of the group in order to leave it
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
        } else if(battle.getStatus().equals(BattleStatus.BATTLE)){
            if(group.getStudents().size() < battle.getMinGroupSize()){
                //every other student in the group must be removed both from the group (see for loop) and from the battle (the caller will do this)
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

        //if null, the group still exists but without the student that left
        //if not null, the group has been kicked from the battle (group size was < minGroupSize) and the caller needs student ids to kick them from the battle
        return group;
    }

    @Override
    public List<StudentGroup> getAllGroupsRepoLinksByBattle(Long battleId) {
        Battle battle = battleRepository.findById(battleId).orElseThrow(BattleDoesNotExistException::new);
        return groupRepository.findScoreByBattle(battle);
    }

    @Override
    public StudentGroup saveRepositoryUrl(SaveGroupRepositoryLinkDto saveGroupRepositoryLinkDto) {
        List<StudentGroup> groups = groupRepository.findByBattle(battleRepository.findById(saveGroupRepositoryLinkDto.getBattleId()).orElseThrow(BattleDoesNotExistException::new));
        for(StudentGroup group : groups){
            for(Student student : group.getStudents()){
                if(student.getStudentId().equals(saveGroupRepositoryLinkDto.getStudentId())){
                    group.setClonedRepositoryLink(saveGroupRepositoryLinkDto.getClonedRepositoryLink());
                    return groupRepository.save(group);
                }
            }
        }
        throw new GroupDoesNotExistsException();
    }

    @Override
    public StudentGroup manuallyEvaluateGroup(Long groupId, float score, Long battleId) {
        Battle battle = battleRepository.findById(battleId).orElseThrow(BattleDoesNotExistException::new);
        if(!battle.getStatus().equals(BattleStatus.CONSOLIDATION))
            throw new CannotEvaluateGroupSolutionException();

        StudentGroup group = groupRepository.findById(groupId).orElseThrow(GroupDoesNotExistsException::new);
        group.setScore(score);
        return groupRepository.save(group);
    }

    @Override
    public List<Student> getStudentsInBattle(Long battleId) {
        Battle battle = battleRepository.findById(battleId).orElseThrow(BattleDoesNotExistException::new);
        List<StudentGroup> groups = groupRepository.findByBattle(battle);
        List<Student> students = new ArrayList<>();
        for(StudentGroup group : groups){
            if(!group.getStudents().isEmpty()){
                students.addAll(group.getStudents());
            }
        }
        return students;
    }

    @Override
    public List<StudentGroup> getGroupsInBattle(Long battleId) {
        Battle battle = battleRepository.findById(battleId).orElseThrow(BattleDoesNotExistException::new);
        return groupRepository.findByBattle(battle);
    }

    @Override
    public StudentGroup getGroupByStudentId(Long battleId, Long userId) {
        Battle battle = battleRepository.findById(battleId).orElseThrow(BattleDoesNotExistException::new);
        List<StudentGroup> groups = groupRepository.findByBattle(battle);
        for(StudentGroup group : groups){
            for(Student student : group.getStudents()){
                if(student.getStudentId().equals(userId)){
                    return group;
                }
            }
        }
        return null;
    }
}
