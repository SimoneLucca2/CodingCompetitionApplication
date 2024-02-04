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
        } catch (JsonProcessingException e) {
            log.error("Error processing JSON: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(new ErrorResponse("Error processing JSON: " + e.getMessage()));
        } catch (BattleStateTooAdvancedException e) {
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
            return ResponseEntity.ok().build();
        } catch (BattleStateTooAdvancedException | GroupIsFullException | StudentAlreadyInAnotherGroupException e) {
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
                 StudentNotMemberOfGroupException e) {
            log.error("Bad request: {}", e.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (JsonProcessingException e) {
            log.error("Error processing JSON: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(new ErrorResponse("Error processing JSON: " + e.getMessage()));
        }
        return ResponseEntity.ok().build();
    }

    @PostMapping(path = "/repo")
    public ResponseEntity<Object> uploadClonedRepoLink(@RequestBody SaveGroupRepositoryLinkDto saveGroupRepositoryLinkDto){ //Argument to be changed
        log.info("A student is trying to upload a cloned repo link with message: {" + saveGroupRepositoryLinkDto + "}");
        try {
            StudentGroup group = groupService.saveRepositoryUrl(saveGroupRepositoryLinkDto);
            log.info("Cloned repo link uploaded successfully");
            return ResponseEntity.ok().body(group);
        } catch (BattleStateTooAdvancedException e) {
            log.error("Bad request: {}", e.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        }
    }

    @GetMapping(path = "/students/{battleId}/{groupId}")
    public ResponseEntity<Object> getStudentsInBattle(@PathVariable Long battleId, @PathVariable Long groupId){
        log.info("Getting students in battle: {" + battleId + "}");
        List<Student> students = groupService.getStudentsInBattle(battleId, groupId);
        List<StudentDto> dtos = new ArrayList<>();
        for(Student student : students){
            dtos.add(
                    StudentDto.builder()
                            .studentId(student.getStudentId())
                            .build()
            );
        }
        return ResponseEntity.ok().body(dtos);
    }
}
