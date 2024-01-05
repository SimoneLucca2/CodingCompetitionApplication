package com.polimi.ckb.battleService.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.polimi.ckb.battleService.dto.StudentInvitesToGroupDto;
import com.polimi.ckb.battleService.dto.StudentJoinsGroupDto;
import com.polimi.ckb.battleService.dto.StudentLeavesGroupDto;
import com.polimi.ckb.battleService.service.GroupService;
import com.polimi.ckb.battleService.service.kafkaProducer.EmailSendingRequestKafkaProducer;
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
    private final EmailSendingRequestKafkaProducer emailSendingRequestKafkaProducer;

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
        }
    }

    @PutMapping
    public ResponseEntity<Object> joinGroup(@RequestBody StudentJoinsGroupDto studentDto){
        log.info("A student is trying to join a group with message: {" + studentDto + "}");
        groupService.joinGroup(studentDto);
        return null;
    }

    @DeleteMapping
    private ResponseEntity<Object> leaveGroup(@RequestBody StudentLeavesGroupDto studentDto ){
        return null;
    }
}
