package com.polimi.ckb.battleService.service.impl;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.polimi.ckb.battleService.config.BattleStatus;
import com.polimi.ckb.battleService.config.TournamentStatus;
import com.polimi.ckb.battleService.dto.*;
import com.polimi.ckb.battleService.entity.Battle;
import com.polimi.ckb.battleService.entity.Student;
import com.polimi.ckb.battleService.entity.StudentGroup;
import com.polimi.ckb.battleService.exception.*;
import com.polimi.ckb.battleService.repository.BattleRepository;
import com.polimi.ckb.battleService.repository.EducatorRepository;
import com.polimi.ckb.battleService.repository.GroupRepository;
import com.polimi.ckb.battleService.repository.StudentRepository;
import com.polimi.ckb.battleService.service.BattleService;
import com.polimi.ckb.battleService.service.kafkaProducer.NewBattleEmailKafkaProducer;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Objects;

@Service
@RequiredArgsConstructor
@Slf4j
public class BattleServiceImpl implements BattleService {
    private final BattleRepository battleRepository;
    private final EducatorRepository educatorRepository;
    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;
    private final RestTemplate restTemplate;
    private final ScoreServiceImpl scoreService;
    private final StudentNotificationService studentNotificationService;

    @Value("${api.gateway.url}")
    private String apiGatewayUrl;

    @Override
    @Transactional
    public Battle createBattle(CreateBattleDto createBattleDto, boolean isTest) throws RuntimeException {

        //check that tournament exists and its status is not CLOSING or CLOSED
        //check that battle creator has access to the tournament
        //If isTest is false, skip tournament checks (performing unit tests)
        if(!isTest){
            TournamentDto tournamentDto;
            try {
                tournamentDto = checkTournamentStats(createBattleDto.getTournamentId());
            } catch (JsonProcessingException e) {
                throw new TournamentDoesNotExistException();
            }
            if(!tournamentDto.getStatus().equals(TournamentStatus.ACTIVE)){
                throw new TournamentNotActiveException();
            }

            if(!createBattleDto.getCreatorId().equals(tournamentDto.getCreatorId()) &&
                    !tournamentDto.getOrganizerIds().contains(createBattleDto.getCreatorId())){
                throw new EducatorNotAuthorizedException();
            }
        }

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

        Battle newBattle = battleRepository.save(convertToEntity(createBattleDto));

        studentNotificationService.sendToRegisteredStudents(newBattle);

        return newBattle;
    }



    /*private String getTournamentServiceUrl() {
        List<ServiceInstance> instances = discoveryClient.getInstances("TOURNAMENT-SERVICE");
        if (instances.isEmpty()) {
            throw new RuntimeException("No instances of tournament service found");
        }
        return instances.get(0).getUri().toString();
    }*/

    //TODO: maybe put this inside a mapper class
    Battle convertToEntity(CreateBattleDto createBattleDto){
        return Battle.builder()
                .name(createBattleDto.getName())
                .description(createBattleDto.getDescription())
                .registrationDeadline(createBattleDto.getRegistrationDeadline())
                .submissionDeadline(createBattleDto.getSubmissionDeadline())
                .status(BattleStatus.PRE_BATTLE)
                .creator(educatorRepository
                        .findById(createBattleDto.getCreatorId())
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
                .score(Float.intBitsToFloat(0))
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

        if (Objects.requireNonNull(battle.getStatus()) == BattleStatus.BATTLE) {//from PRE_BATTLE only BATTLE status is allowed
            //check if every group satisfies the constraints, if not kick its student from the battle and delete it
            checkGroupsConstraints(battle);
        }

        //if status closed send message to kafka to update the tournament score
        else if (battle.getStatus() == BattleStatus.CONSOLIDATION){
            scoreService.sendScoreForEachStudent(battle.getBattleId());
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

    }

    public Battle closeBattle(ChangeBattleStatusDto closeBattleDto) {
        Battle battle = battleRepository.findById(closeBattleDto.getBattleId())
                .orElseThrow(BattleDoesNotExistException::new);

        if (battle.getStatus() != BattleStatus.CONSOLIDATION) {
            throw new BattleChangingStatusException("Cannot close battle");
        }

        battle.setStatus(BattleStatus.CLOSED);
        scoreService.sendScoreForEachStudent(battle.getBattleId());
        return battleRepository.save(battle);
    }

    TournamentDto checkTournamentStats(Long tournamentId) throws JsonProcessingException {
        log.info("Checking tournament existence and status and creator's access to it");
        //String tournamentServiceUrl = getTournamentServiceUrl();
        String url = apiGatewayUrl + "/tournament/" + tournamentId;
        ResponseEntity<TournamentDto> response = restTemplate.getForEntity(url, TournamentDto.class, tournamentId);
        return response.getBody();
    }

    @Override
    public void closeAllBattleInTournament(Long tournamentId){
        List<Battle> battles = battleRepository.findByTournamentId(tournamentId);
        for(Battle battle : battles){
            battle.setStatus(BattleStatus.CLOSED);
            battleRepository.save(battle);
        }
    }

    @Transactional
    @Override
    public List<Battle> getBattlesByTournamentId(Long tournamentId){
        return battleRepository.findByTournamentId(tournamentId);
    }

    @Transactional
    @Override
    public void saveRepositoryUrl(@RequestBody SaveRepositoryLinkDto saveRepositoryUrlDto){
        Battle battle = battleRepository.findById(saveRepositoryUrlDto.getBattleId())
                .orElseThrow(BattleDoesNotExistException::new);

        battle.setRepoLink(saveRepositoryUrlDto.getRepositoryUrl());
        battleRepository.save(battle);
    }

    @Transactional
    @Override
    public void deleteBattle(DeleteBattleDto deleteBattleDto){
        battleRepository.deleteById(deleteBattleDto.getBattleId());
    }

    @Override
    public void quitEntireTournament(StudentQuitTournamentDto studentQuitTournamentDto) {
        List<Battle> battles = battleRepository.findByTournamentId(studentQuitTournamentDto.getTournamentId());
        if(battles.isEmpty())
            return;

        for(Battle battle : battles){
            List<StudentGroup> groups = groupRepository.findByBattle(battle);
            if(groups.isEmpty())
                continue;

            for(StudentGroup group : groups){
                if(group.getStudents().contains(studentQuitTournamentDto.getStudentId())){
                    this.leaveBattle(
                            StudentLeaveBattleDto.builder()
                                    .battleId(battle.getBattleId())
                                    .studentId(studentQuitTournamentDto.getStudentId())
                                    .build()
                    );
                }
            }
        }
    }
}
