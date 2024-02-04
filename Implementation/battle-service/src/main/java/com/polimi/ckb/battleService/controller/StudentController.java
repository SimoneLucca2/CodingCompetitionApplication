package com.polimi.ckb.battleService.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.polimi.ckb.battleService.dto.ErrorResponse;
import com.polimi.ckb.battleService.dto.GroupDto;
import com.polimi.ckb.battleService.dto.StudentJoinBattleDto;
import com.polimi.ckb.battleService.dto.StudentLeaveBattleDto;
import com.polimi.ckb.battleService.entity.Student;
import com.polimi.ckb.battleService.entity.StudentGroup;
import com.polimi.ckb.battleService.exception.BattleDoesNotExistException;
import com.polimi.ckb.battleService.exception.BattleStateTooAdvancedException;
import com.polimi.ckb.battleService.exception.StudentDoesNotExistException;
import com.polimi.ckb.battleService.exception.StudentNotRegisteredInBattleException;
import com.polimi.ckb.battleService.service.BattleService;
import com.polimi.ckb.battleService.service.kafkaProducer.StudentJoinBattleProducer;
import com.polimi.ckb.battleService.service.kafkaProducer.StudentLeaveBattleProducer;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping(path = "/battle/student")
@AllArgsConstructor
@Slf4j
public class StudentController {
    private final BattleService battleservice;
    private final StudentJoinBattleProducer studentJoinBattleProducer;
    private final StudentLeaveBattleProducer studentLeaveBattleProducer;

    @PostMapping
    public ResponseEntity<Object> joinBattle(@RequestBody StudentJoinBattleDto studentDto) {
        log.info("A student is trying to join a battle with message: {" + studentDto + "}");
        try {
            StudentGroup group = battleservice.joinBattle(studentDto);
            studentJoinBattleProducer.sendStudentJoinsBattleMessage(studentDto);
            log.info("Student joined battle successfully");
            return ResponseEntity.ok().body(
                    GroupDto.builder()
                            .groupId(group.getGroupId())
                            .build()
            );
        } catch (StudentDoesNotExistException | BattleDoesNotExistException | BattleStateTooAdvancedException e) {
            log.error("Bad request: {}", e.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (JsonProcessingException e) {
            log.error("Error processing JSON: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(new ErrorResponse("Error processing JSON: " + e.getMessage()));
        }
    }

    @DeleteMapping
    public ResponseEntity<Object> leaveBattle(@RequestBody StudentLeaveBattleDto studentDto) {
        log.info("A student is trying to leave a battle with message: {" + studentDto + "}");
        StudentGroup group;
        try {
            group = battleservice.leaveBattle(studentDto);
            studentLeaveBattleProducer.sendStudentLeavesBattleMessage(studentDto);
            log.info("Student left battle successfully");

            if (group != null) {
                log.info("Group does no more satisfy the requirements, sending message to kick students from the battle");
                for (Student student : group.getStudents()) {
                    studentLeaveBattleProducer.sendStudentLeavesBattleMessage(
                            StudentLeaveBattleDto.builder()
                                    .studentId(student.getStudentId())
                                    .battleId(studentDto.getBattleId())
                                    .build()
                    );
                }
                log.info("All students correctly kicked from the battle");
            }
            return ResponseEntity.ok().build();
        } catch (StudentDoesNotExistException | BattleDoesNotExistException | StudentNotRegisteredInBattleException | BattleStateTooAdvancedException e) {
            log.error("Bad request: {}", e.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (JsonProcessingException e) {
            log.error("Error processing JSON: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(new ErrorResponse("Error processing JSON: " + e.getMessage()));
        }
    }
}
