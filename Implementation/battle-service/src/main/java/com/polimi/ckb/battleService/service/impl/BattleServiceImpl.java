package com.polimi.ckb.battleService.service.impl;

import com.polimi.ckb.battleService.config.BattleStatus;
import com.polimi.ckb.battleService.dto.ChangeBattleStatusDto;
import com.polimi.ckb.battleService.dto.CreateBattleDto;
import com.polimi.ckb.battleService.dto.StudentJoinBattleDto;
import com.polimi.ckb.battleService.dto.StudentLeaveBattleDto;
import com.polimi.ckb.battleService.entity.Battle;
import com.polimi.ckb.battleService.entity.Student;
import com.polimi.ckb.battleService.entity.StudentGroup;
import com.polimi.ckb.battleService.exception.*;
import com.polimi.ckb.battleService.repository.BattleRepository;
import com.polimi.ckb.battleService.repository.EducatorRepository;
import com.polimi.ckb.battleService.repository.GroupRepository;
import com.polimi.ckb.battleService.repository.StudentRepository;
import com.polimi.ckb.battleService.service.BattleService;
import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import javax.validation.Valid;
import java.util.List;

@Service
@AllArgsConstructor
public class BattleServiceImpl implements BattleService {
    private final BattleRepository battleRepository;
    private final EducatorRepository educatorRepository;
    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;

    @Override
    @Transactional
    public Battle createBattle(CreateBattleDto createBattleDto) throws RuntimeException {
        //check that tournament exists and its status is not CLOSING or CLOSED
        //check that battle creator has access to the tournament
        //TODO: check the existence of the creator and of the tournament with HTTP request to TournamentService

        //check if battle already exists within the same tournament
        List<Battle> battleWithinSameTournament = battleRepository.findByTournamentId(createBattleDto.getTournamentId());

        if(battleWithinSameTournament != null){
            for(Battle battle : battleWithinSameTournament) {
                if (battle.getName().equals(createBattleDto.getName())) {
                    throw new BattleAlreadyExistException();
                }
            }

            //check if deadlines are ok with other battles' ones (battles cannot overlap)
            for(Battle battle : battleWithinSameTournament) {
                if(battle.getStatus().equals(BattleStatus.PRE_BATTLE) || battle.getStatus().equals(BattleStatus.BATTLE)){
                    final boolean check1 = createBattleDto.getRegistrationDeadline().compareTo(battle.getRegistrationDeadline()) < 0 &&
                                            createBattleDto.getSubmissionDeadline().compareTo(battle.getSubmissionDeadline()) < 0 &&
                                            createBattleDto.getSubmissionDeadline().compareTo(battle.getRegistrationDeadline()) <= 0;

                    final boolean check2 = createBattleDto.getRegistrationDeadline().compareTo(battle.getRegistrationDeadline()) > 0 &&
                                            createBattleDto.getSubmissionDeadline().compareTo(battle.getSubmissionDeadline()) > 0 &&
                                            createBattleDto.getRegistrationDeadline().compareTo(battle.getSubmissionDeadline()) >= 0;

                    if(!(check1 || check2)){
                        throw new BattleDeadlinesOverlapException();
                    }
                }
            }
        }

        return battleRepository.save(convertToEntity(createBattleDto));
    }

    //TODO: maybe put this inside a mapper class
    private Battle convertToEntity(CreateBattleDto createBattleDto){
        return Battle.builder()
                .name(createBattleDto.getName())
                .description(createBattleDto.getDescription())
                .registrationDeadline(createBattleDto.getRegistrationDeadline())
                .submissionDeadline(createBattleDto.getSubmissionDeadline())
                .status(BattleStatus.PRE_BATTLE)
                .creatorId(educatorRepository
                        .findById(createBattleDto.getCreatorId().getEducatorId())
                        .orElseThrow(() -> new RuntimeException("Educator not found")))
                .tournamentId(createBattleDto.getTournamentId())
                .maxGroupSize(createBattleDto.getMaxGroupSize())
                .minGroupSize(createBattleDto.getMinGroupSize())
                .build();
    }

    @Override
    @Transactional
    public StudentGroup joinBattle(@Valid StudentJoinBattleDto studentDto) {
        Battle battle = battleRepository.findById(studentDto.getBattleId())
                .orElseThrow(BattleDoesNotExistException::new);

        //battle can be joined only if its status is PRE_BATTLE
        if(battle.getStatus() != BattleStatus.PRE_BATTLE){
            throw new BattleStateTooAdvancedException();
        }

        Student student = studentRepository.findById(studentDto.getStudentId())
                .orElseThrow(StudentDoesNotExistException::new);

        //check if student is already registered to the battle
        List<StudentGroup> registeredGroups = groupRepository.findByBattle(battle);
        for (StudentGroup group : registeredGroups) {
            if (group.getStudents().contains(student)) {
                throw new StudentAlreadyRegisteredToBattleException();
            }
        }

        //student joins the battle as singleton group
        StudentGroup newStudentGroup = StudentGroup.builder()
                .battle(battle)
                .score(0)
                .build();
        newStudentGroup.getStudents().add(student);
        battle.getStudentGroups().add(newStudentGroup);
        student.getStudentGroups().add(newStudentGroup);

        newStudentGroup = groupRepository.save(newStudentGroup);
        battleRepository.save(battle);
        studentRepository.save(student);
        return newStudentGroup;
    }

    @Transactional
    @Override
    public StudentGroup leaveBattle(StudentLeaveBattleDto studentDto) {
        Battle battle = battleRepository.findById(studentDto.getBattleId())
                .orElseThrow(BattleDoesNotExistException::new);

        Student student = studentRepository.findById(studentDto.getStudentId())
                .orElseThrow(StudentDoesNotExistException::new);

        //find the group the student is member of
        List<StudentGroup> registeredGroups = groupRepository.findByBattle(battle);
        StudentGroup leavingStudentGroup = null;
        boolean check = false;
        for (StudentGroup group : registeredGroups) {
            if (group.getStudents().contains(student)) {
                check = true;
                leavingStudentGroup = group;
                //when first group is found, the system can move on (student cannot be member of more than one group in the same battle)
                break;
            }
        }

        //if check is false, student is not member of any group, hence is not registered to the battle
        if (!check) {
            throw new StudentNotRegisteredInBattleException();
        }


        /*  Check battle's status:
            PRE_BATTLE -> if group size is not greater than minGroupSize, no problem for now
                          if group size is zero then delete the group
            BATTLE -> if group size is not greater than minGroupSize, group must be disjointed
                      if group size is zero then delete the group
            other -> throw exception
         */
        if(battle.getStatus().equals(BattleStatus.CONSOLIDATION) || battle.getStatus().equals(BattleStatus.CLOSED)) {
            throw new BattleStateTooAdvancedException();
        }

        //remove student from the group
        leavingStudentGroup.getStudents().remove(student);
        student.getStudentGroups().remove(leavingStudentGroup);
        studentRepository.save(student);

        if(battle.getStatus().equals(BattleStatus.PRE_BATTLE)){
            //need only to check if group is now empty and delete it
            if(leavingStudentGroup.getStudents().isEmpty()){
                battle.getStudentGroups().remove(leavingStudentGroup);
                battleRepository.save(battle);
                groupRepository.delete(leavingStudentGroup);
                leavingStudentGroup = null;
            }
        } else if(battle.getStatus().equals(BattleStatus.BATTLE)){
            //need to check group constraints and eventually kick the entire group from the battle
            if(leavingStudentGroup.getStudents().size() < battle.getMinGroupSize()){
                for(Student studentInLeavingGroup: leavingStudentGroup.getStudents()){
                    studentInLeavingGroup.getStudentGroups().remove(leavingStudentGroup);
                    studentRepository.save(studentInLeavingGroup);
                }
            } else {
                leavingStudentGroup = null;
            }
        }

        //if null, group still exists (without the leaving student)
        //if not null, the group has been deleted but caller needs student info to send kafka messages
        return leavingStudentGroup;
    }

    @Override
    @Transactional
    public Battle changeBattleStatus(ChangeBattleStatusDto changeBattleStatusDto) {
        Battle battle = battleRepository.findById(changeBattleStatusDto.getBattleId())
                .orElseThrow(BattleDoesNotExistException::new);

        switch(battle.getStatus()){
            case PRE_BATTLE:
                //from PRE_BATTLE only BATTLE status is allowed
                if(changeBattleStatusDto.getStatus().equals(BattleStatus.BATTLE)){
                    battle.setStatus(BattleStatus.BATTLE);

                    //check if every group satisfies the constraints, if not kick its student from the battle and delete it
                    checkGroupsConstraints(battle);
                } else {
                    throw new BattleChangingStatusException("Cannot switch from PRE_BATTLE to " + changeBattleStatusDto.getStatus());
                }
                break;

            case BATTLE:
                //from BATTLE only CONSOLIDATION status is allowed
                if(changeBattleStatusDto.getStatus().equals(BattleStatus.CONSOLIDATION))
                    battle.setStatus(BattleStatus.CONSOLIDATION);
                //TODO: code evaluation and score update
                else
                    throw new BattleChangingStatusException("Cannot switch from BATTLE to " + changeBattleStatusDto.getStatus());
                break;

            case CONSOLIDATION:
                //from CONSOLIDATION only CLOSED status is allowed
                if(changeBattleStatusDto.getStatus().equals(BattleStatus.CLOSED))
                    battle.setStatus(BattleStatus.CLOSED);
                //TODO: idk
                else
                    throw new BattleChangingStatusException("Cannot switch from CONSOLIDATION to " + changeBattleStatusDto.getStatus());
                break;

            default:
                //from CLOSED no status change is allowed
                throw new BattleChangingStatusException("Cannot switch status");
        }

        //save new status
        return battleRepository.save(battle);
    }

    private void checkGroupsConstraints(Battle battle){
        //list of groups registered to the given battle
        List<StudentGroup> registeredGroups = groupRepository.findByBattle(battle);
        for(StudentGroup group: registeredGroups){
            //if group size is less than minGroupSize, kick all its students from the battle and delete the group
            if(group.getStudents().size() < battle.getMinGroupSize()){
                for(Student studentInGroup: group.getStudents()){
                    studentInGroup.getStudentGroups().remove(group);
                    studentRepository.save(studentInGroup);
                }
                battle.getStudentGroups().remove(group);
                battleRepository.save(battle);
                groupRepository.delete(group);
            }
        }

        //TODO: TO BE FINISHED (kafka messages about kicking students is missing)
    }
}
