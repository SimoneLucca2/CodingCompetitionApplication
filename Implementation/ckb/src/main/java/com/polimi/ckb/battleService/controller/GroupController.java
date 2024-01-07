package com.polimi.ckb.battleService.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.polimi.ckb.battleService.dto.StudentInvitesToGroupDto;
import com.polimi.ckb.battleService.dto.StudentJoinsGroupDto;
import com.polimi.ckb.battleService.dto.StudentLeaveBattleDto;
import com.polimi.ckb.battleService.dto.StudentLeavesGroupDto;
import com.polimi.ckb.battleService.entity.Student;
import com.polimi.ckb.battleService.entity.StudentGroup;
import com.polimi.ckb.battleService.exception.*;
import com.polimi.ckb.battleService.service.BattleService;
import com.polimi.ckb.battleService.service.GroupService;
import com.polimi.ckb.battleService.service.kafkaProducer.EmailSendingRequestKafkaProducer;
import com.polimi.ckb.battleService.service.kafkaProducer.StudentLeaveBattleProducer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/battle/group")
@AllArgsConstructor
@Slf4j
public class GroupController {
    private final GroupService groupService;
    private final BattleService battleService;
    private final EmailSendingRequestKafkaProducer emailSendingRequestKafkaProducer;
    private final StudentLeaveBattleProducer studentLeaveBattleProducer;

    @PostMapping
    public ResponseEntity<Object> inviteStudentToGroup(@RequestBody StudentInvitesToGroupDto studentDto){
        log.info("A student is trying to invite another student to a group with message: {" + studentDto + "}");
        groupService.inviteStudentToGroup(studentDto);
        try {
            emailSendingRequestKafkaProducer.sendEmailSendingRequestMessage(studentDto);
            log.info("Email sending request successfully done");
            return ResponseEntity.ok().build();
        } catch (JsonProcessingException e) {
            log.error("Error processing JSON: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        } catch (BattleStateTooAdvancedException e) {
            log.error("Bad request: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping
    public ResponseEntity<Object> joinGroup(@RequestBody StudentJoinsGroupDto studentDto){
        log.info("A student is trying to join a group with message: {" + studentDto + "}");
        try {
            groupService.joinGroup(studentDto);
            return ResponseEntity.ok().build();
        } catch (BattleStateTooAdvancedException | GroupIsFullException | StudentAlreadyInAnotherGroupException e) {
            log.error("Bad request: {}", e.getMessage());
            return ResponseEntity.badRequest().build();
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
            return ResponseEntity.badRequest().build();
    } catch (JsonProcessingException e) {
            log.error("Error processing JSON: {}", e.getMessage());
            return ResponseEntity.internalServerError().build();
        }
        return ResponseEntity.ok().build();
    }
}
