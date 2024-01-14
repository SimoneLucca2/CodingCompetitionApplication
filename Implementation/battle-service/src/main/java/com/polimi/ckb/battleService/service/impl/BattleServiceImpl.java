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

    @Transactional
    public Battle createBattle(CreateBattleDto createBattleDto) throws RuntimeException {
        List<Battle> battleWithinSameTournament = battleRepository.findByTournamentId(createBattleDto.getTournamentId());

        //TODO: check the existence of the creator and of the tournament with HTTP request to TournamentService

        //check if it already exists a battle with the same name in the tournament
        if(battleWithinSameTournament != null){
            for(Battle battle : battleWithinSameTournament) {
                if (battle.getName().equals(createBattleDto.getName())) {
                    throw new BattleAlreadyExistException();
                }
            }

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

        if(battle.getStatus() != BattleStatus.PRE_BATTLE){
            throw new BattleStateTooAdvancedException();
        }

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
                    leavingStudentGroup = null;
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

    @Override
    public Battle changeBattleStatus(ChangeBattleStatusDto changeBattleStatusDto) {
        Battle battle = battleRepository.findById(changeBattleStatusDto.getBattleId())
                .orElseThrow(BattleDoesNotExistException::new);

        switch(battle.getStatus()){
            case PRE_BATTLE:
                if(changeBattleStatusDto.getStatus().equals(BattleStatus.BATTLE)){
                    battle.setStatus(BattleStatus.BATTLE);
                    //TODO: check group constraints
                } else {
                    throw new BattleChangingStatusException("Cannot switch from PRE_BATTLE to " + changeBattleStatusDto.getStatus());
                }
                break;

            case BATTLE:
                if(changeBattleStatusDto.getStatus().equals(BattleStatus.CONSOLIDATION))
                    battle.setStatus(BattleStatus.CONSOLIDATION);
                //TODO: code evaluation and score update
                else
                    throw new BattleChangingStatusException("Cannot switch from BATTLE to " + changeBattleStatusDto.getStatus());
                break;

            case CONSOLIDATION:
                if(changeBattleStatusDto.getStatus().equals(BattleStatus.CLOSED))
                    battle.setStatus(BattleStatus.CLOSED);
                //TODO: idk
                else
                    throw new BattleChangingStatusException("Cannot switch from CONSOLIDATION to " + changeBattleStatusDto.getStatus());
                break;

            default: throw new BattleChangingStatusException("Cannot switch status");
        }

        return battleRepository.save(battle);
    }
}
