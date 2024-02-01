package com.polimi.ckb.battleService.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.polimi.ckb.battleService.dto.*;
import com.polimi.ckb.battleService.entity.Battle;
import com.polimi.ckb.battleService.exception.*;
import com.polimi.ckb.battleService.service.BattleService;
import com.polimi.ckb.battleService.service.GitService;
import com.polimi.ckb.battleService.service.kafkaProducer.BattleCreationKafkaProducer;
import com.polimi.ckb.battleService.utility.CreateBattleFilter;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.*;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/battle")
@Slf4j
public class BattleController {
    private final BattleService battleService;
    private final GitService gitService;
    private final BattleCreationKafkaProducer kafkaProducer;

    @PostMapping
    public ResponseEntity<Object> createBattle(@RequestBody @Valid CreateBattleDto createBattleDto) {
        Battle createdBattle = null;
        try {
            CreateBattleFilter.check(createBattleDto);

            //If battle name is "TEST" then the caller is a test method (i.e. isTest = true)
            //Otherwise, the caller is a real user (i.e. isTest = false)
            if (createBattleDto.getName().equals("TEST"))
                createdBattle = battleService.createBattle(createBattleDto, true);
            else {
                createdBattle = battleService.createBattle(createBattleDto, false);
                String repositoryUrl = gitService.createGitHubRepository(createBattleDto);

                //save the link in battle entity
                battleService.saveRepositoryUrl(
                        SaveRepositoryLinkDto.builder()
                                .repositoryUrl(repositoryUrl)
                                .battleId(createdBattle.getBattleId())
                                .build()
                );

                //push and commit a file yaml to set up an action that informs the system about new pushes on main branch
                log.info("Pushing the configuration yaml file into the new repository");
                gitService.uploadSetupFiles(repositoryUrl, createdBattle.getName());
                log.info("Repository ready");
            }

            kafkaProducer.sendBattleCreationMessage(CreatedBattleDto.from(createdBattle));
            log.info("Battle created successfully");
            return ResponseEntity.ok().body(CreatedBattleDto.from(createdBattle));
        } catch (BattleAlreadyExistException | BattleDeadlinesOverlapException | TournamentNotActiveException |
                 EducatorNotAuthorizedException | BattleDeadlineException | BattleGroupConstraintException e) {
            log.error("Bad request: {}", e.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (JsonProcessingException e) {
            log.error("Error processing JSON: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(new ErrorResponse("Error processing JSON: " + e.getMessage()));
        } catch (ErrorWhileCreatingRepositoryException | GitAPIException | IOException e) {
            log.error("Error while creating repository {}", e.getMessage());
            battleService.deleteBattle(
                    DeleteBattleDto.builder()
                            .battleId(createdBattle.getBattleId())
                            .build()
            );
            return ResponseEntity.internalServerError().body(new ErrorResponse("Error while creating repository: " + e.getMessage()));
        }
    }

    @GetMapping(path = "/all/{tournamentId}")
    public ResponseEntity<Object> getBattle(@PathVariable Long tournamentId) {
        log.info("Getting battles with message from tournament: {" + tournamentId + "}");
        List<Battle> battles = battleService.getBattlesByTournamentId(tournamentId);
        log.info("Battle retrieved successfully");
        if(battles.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok().body(battles);
    }
}




