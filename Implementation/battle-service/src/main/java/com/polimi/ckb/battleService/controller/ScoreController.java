package com.polimi.ckb.battleService.controller;

import com.polimi.ckb.battleService.dto.ErrorResponse;
import com.polimi.ckb.battleService.dto.NewPushDto;
import com.polimi.ckb.battleService.service.BattleService;
import com.polimi.ckb.battleService.service.GitService;
import com.polimi.ckb.battleService.service.GroupService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.web.bind.annotation.*;
import org.springframework.http.ResponseEntity;

import java.io.IOException;


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
        } catch (GitAPIException | IOException e) {
            throw new RuntimeException(e);
        }
    }

    @GetMapping(path = "/all")
    public ResponseEntity<Object> getAllGroupsRepoLinksByBattle(@RequestParam("battleId") String battleId){
        log.info("A request to get all the groups repo links has been performed");
        return ResponseEntity.ok(groupService.getAllGroupsRepoLinksByBattle(battleId));
    }
}
