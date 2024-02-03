package com.polimi.ckb.battleService.controller;

import com.polimi.ckb.battleService.dto.GroupScoreDto;
import com.polimi.ckb.battleService.dto.NewPushDto;
import com.polimi.ckb.battleService.entity.StudentGroup;
import com.polimi.ckb.battleService.exception.BattleDoesNotExistException;
import com.polimi.ckb.battleService.exception.CannotEvaluateGroupSolutionException;
import com.polimi.ckb.battleService.exception.ErrorWhileExecutingScannerException;
import com.polimi.ckb.battleService.exception.GroupDoesNotExistsException;
import com.polimi.ckb.battleService.service.GitService;
import com.polimi.ckb.battleService.service.GroupService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;


@RestController
@RequestMapping(path = "/battle/score")
@Slf4j
@AllArgsConstructor
public class ScoreController {
    private final GroupService groupService;
    private final GitService gitService;

    @PostMapping
    public void checkNewPushOnMainBranch(@RequestBody NewPushDto newPushDto){
        log.info("A new push on the main branch has been performed on: " + newPushDto.getRepositoryUrl());
        try {
            gitService.calculateTemporaryScore(newPushDto);
        } catch (GitAPIException | IOException | ErrorWhileExecutingScannerException | InterruptedException |
                 GroupDoesNotExistsException e) {
            log.error("Internal server error: " + e.getMessage());
        } catch (CannotEvaluateGroupSolutionException e){
            log.error("Bad request {}: ", e.getMessage());
        }
    }

    @GetMapping(path = "/{battleId}/all")
    public ResponseEntity<Object> getAllGroupsRepoLinksByBattle(@PathVariable(name = "battleId") Long battleId){
        log.info("A request to get all the groups repo links has been performed");
        try{
            List<StudentGroup> groups = groupService.getAllGroupsRepoLinksByBattle(battleId);
            List<GroupScoreDto> dtos = new ArrayList<>();
            for(StudentGroup group : groups){
                dtos.add(
                        GroupScoreDto.builder()
                                .groupId(group.getGroupId())
                                .clonedRepositoryLink(group.getClonedRepositoryLink())
                                .score(group.getScore())
                                .build()
                );
            }
            return ResponseEntity.ok(dtos);
        } catch (BattleDoesNotExistException e){
            log.error("Bad request: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }

    @PutMapping(path = "/{battleId}/{groupId}/{score}")
    public ResponseEntity<Object> manuallyEvaluateGroup(@PathVariable(name = "groupId") Long groupId, @PathVariable float score, @PathVariable("battleId") Long battleId){
        log.info("A request to manually evaluate a group has been performed");
        try{
            StudentGroup group = groupService.manuallyEvaluateGroup(groupId, score, battleId);
            log.info("New score registered");
            return ResponseEntity.ok().body(group);
        } catch (GroupDoesNotExistsException | CannotEvaluateGroupSolutionException e){
            log.error("Bad request: {}", e.getMessage());
            return ResponseEntity.badRequest().body(e.getMessage());
        }
    }
}
