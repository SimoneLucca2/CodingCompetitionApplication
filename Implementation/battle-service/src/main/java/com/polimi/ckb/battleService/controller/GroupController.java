package com.polimi.ckb.battleService.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.polimi.ckb.battleService.dto.*;
import com.polimi.ckb.battleService.entity.Student;
import com.polimi.ckb.battleService.entity.StudentGroup;
import com.polimi.ckb.battleService.exception.*;
import com.polimi.ckb.battleService.service.BattleService;
import com.polimi.ckb.battleService.service.GroupService;
import com.polimi.ckb.battleService.service.impl.StudentNotificationService;
import com.polimi.ckb.battleService.service.kafkaProducer.StudentLeaveBattleProducer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.scheduling.annotation.Async;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping(path = "/battle/group")
@AllArgsConstructor
@Slf4j
public class GroupController {
    private final GroupService groupService;
    private final BattleService battleService;
    private final StudentNotificationService studentNotificationService;
    private final StudentLeaveBattleProducer studentLeaveBattleProducer;

    @PostMapping
    public ResponseEntity<Object> inviteStudentToGroup(@RequestBody StudentInvitesToGroupDto studentDto){
        log.info("A student is trying to invite another student to a group with message: {" + studentDto + "}");
        groupService.inviteStudentToGroup(studentDto);
        try {
            studentNotificationService.sendInvitationToStudent(studentDto);
            log.info("Email sending request successfully done");
            return ResponseEntity.ok().build();
        } catch (BattleStateTooAdvancedException | BattleDoesNotExistException | GroupDoesNotExistsException e) {
            log.error("Bad request: {}", e.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch(Exception e){
            log.error("Internal server error: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(new ErrorResponse("Internal server error: " + e.getMessage()));
        }
    }

    @PutMapping
    public ResponseEntity<Object> joinGroup(@RequestBody StudentJoinsGroupDto studentDto){
        log.info("A student is trying to join a group with message: {" + studentDto + "}");
        try {
            StudentGroup group = groupService.joinGroup(studentDto);
            log.info("Student joined group successfully");
            return ResponseEntity.ok().body(
                    GroupDto.builder()
                            .groupId(group.getGroupId())
                            .build()
            );
        } catch (BattleStateTooAdvancedException | GroupIsFullException | StudentAlreadyInAnotherGroupException | BattleDoesNotExistException |
                GroupDoesNotExistsException e) {
            log.error("Bad request: {}", e.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @DeleteMapping
    private ResponseEntity<Object> leaveGroup(@RequestBody StudentLeavesGroupDto studentDto){
        log.info("A student is trying to leave a group with message: {" + studentDto + "}");
        StudentGroup group;
        try {
            group = groupService.leaveGroup(studentDto);
            log.info("Student left group successfully");

            if (group != null) {
                log.info("Group does no more satisfy the requirements, sending message to kick students from the battle");
                for (Student student : group.getStudents()) {
                    studentLeaveBattleProducer.sendStudentLeavesBattleMessage(
                            StudentLeaveBattleDto.builder()
                                    .studentId(student.getStudentId())
                                    .battleId(group.getBattle().getBattleId())
                                    .build()
                    );
                }
                StudentLeaveBattleDto studentThatLeaves = StudentLeaveBattleDto.builder()
                        .studentId(studentDto.getStudentId())
                        .battleId(group.getBattle().getBattleId())
                        .build();
                battleService.leaveBattle(studentThatLeaves);
                studentLeaveBattleProducer.sendStudentLeavesBattleMessage(studentThatLeaves);
                log.info("All students correctly kicked from the battle");
            }
        } catch (BattleStateTooAdvancedException | StudentNotRegisteredInBattleException |
                 StudentNotMemberOfGroupException | StudentDoesNotExistException | GroupDoesNotExistsException |
                BattleDoesNotExistException e) {
            log.error("Bad request: {}", e.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (JsonProcessingException e) {
            log.error("Error processing JSON: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(new ErrorResponse("Error processing JSON: " + e.getMessage()));
        }
        return ResponseEntity.ok().build();
    }

    //Upload the cloned repo link of the group
    @PostMapping(path = "/repo")
    public ResponseEntity<Object> uploadClonedRepoLink(@RequestBody SaveGroupRepositoryLinkDto saveGroupRepositoryLinkDto){ //Argument to be changed
        log.info("A student is trying to upload a cloned repo link with message: {" + saveGroupRepositoryLinkDto + "}");
        try {
            StudentGroup group = groupService.saveRepositoryUrl(saveGroupRepositoryLinkDto);
            log.info("Cloned repo link uploaded successfully");
            return ResponseEntity.ok().body(
                    GroupDto.builder()
                            .groupId(group.getGroupId())
                            .build()
            );
        } catch (BattleStateTooAdvancedException e) {
            log.error("Bad request: {}", e.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    //Get all the students in the given battle except the ones in the given group
    @GetMapping(path = "/students/{battleId}")
    public ResponseEntity<Object> getStudentsInBattle(@PathVariable Long battleId){
        log.info("Getting students in battle: {" + battleId + "}");
        List<Student> students = groupService.getStudentsInBattle(battleId);
        if(students.isEmpty())
            return ResponseEntity.noContent().build();

        List<Long> studentsId = new ArrayList<>();
        for(Student student : students){
            studentsId.add(
                    student.getStudentId()
            );
        }
        return ResponseEntity.ok().body(studentsId);
    }

    //Get all the group ids in the given battle
    @GetMapping(path = "all/{battleId}")
    public ResponseEntity<Object> getGroupsInBattle(@PathVariable Long battleId){
        log.info("Getting groups in battle: {" + battleId + "}");
        List<StudentGroup> groups = groupService.getGroupsInBattle(battleId);
        if(groups.isEmpty())
            return ResponseEntity.noContent().build();

        List<Long> ids = new ArrayList<>();
        for(StudentGroup group : groups){
            ids.add(
                    group.getGroupId()
            );
        }
        return ResponseEntity.ok().body(ids);
    }

    //Get the group id of the given student in the given battle
    @GetMapping(path = "/id/{battleId}/{userId}")
    public ResponseEntity<Object> getGroupByStudentId(@PathVariable Long battleId, @PathVariable Long userId){
        log.info("Getting group by student id: {" + userId + "}");
        StudentGroup group = groupService.getGroupByStudentId(battleId, userId);
        if(group == null)
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok().body(
                    GroupDto.builder()
                            .groupId(group.getGroupId())
                            .build()
            );
    }
}
