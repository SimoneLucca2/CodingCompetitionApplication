package com.polimi.ckb.battleService.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.polimi.ckb.battleService.dto.CreateBattleDto;
import com.polimi.ckb.battleService.dto.ErrorResponse;
import com.polimi.ckb.battleService.entity.Battle;
import com.polimi.ckb.battleService.exception.*;
import com.polimi.ckb.battleService.service.BattleService;
import com.polimi.ckb.battleService.service.kafkaProducer.BattleCreationKafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;
import java.io.DataOutputStream;
import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/battle")
@Slf4j
public class BattleController {
    private final BattleService battleService;
    private final BattleCreationKafkaProducer kafkaProducer;
    @Value("${github.api.token}")
    private String gitHubToken;

    @PostMapping
    public ResponseEntity<Object> createBattle(@RequestBody @Valid CreateBattleDto createBattleDto) {
        try {
            log.info("Creating battle with message: {" + createBattleDto + "}");
            if (createBattleDto.getSubmissionDeadline().compareTo(createBattleDto.getRegistrationDeadline()) <= 0) {
                log.error("Submission deadline is before registration deadline or they are the same");
                return ResponseEntity.badRequest().body(new ErrorResponse("Submission deadline is before registration deadline or they are the same"));
            }
            if (createBattleDto.getMaxGroupSize() < createBattleDto.getMinGroupSize()) {
                log.error("Max group size is less than min group size");
                return ResponseEntity.badRequest().body(new ErrorResponse("Max group size is less than min group size"));
            }
            Battle createdBattle = battleService.createBattle(createBattleDto);

            //TODO: create github repository for the battle using GitHub API
            createGitHubRepository(createBattleDto);

            kafkaProducer.sendBattleCreationMessage(createBattleDto);
            log.info("Battle created successfully");
            return ResponseEntity.ok().body(createdBattle);
        } catch (BattleAlreadyExistException | BattleDeadlinesOverlapException | TournamentNotActiveException |
                 EducatorNotAuthorizedException | ErrorWhileCreatingRepositoryException e) {
            log.error("Bad request: {}", e.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (JsonProcessingException e) {
            log.error("Error processing JSON: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(new ErrorResponse("Error processing JSON: " + e.getMessage()));
        }
    }

    private void createGitHubRepository(CreateBattleDto createBattleDto) {
        try {
            final String requestBody = "{\"name\":\"" + createBattleDto.getName() + "\",\"description\":\"" + createBattleDto.getDescription() + "\"}";

            //get connection with GitHub api
            HttpURLConnection connection = getHttpURLConnection();

            try (DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream())) {
                //get data from connection output stream
                outputStream.write(requestBody.getBytes(StandardCharsets.UTF_8));
                outputStream.flush();
            }

            //check response code
            int responseCode = connection.getResponseCode();
            if(responseCode != HttpURLConnection.HTTP_CREATED){
                throw new ErrorWhileCreatingRepositoryException(createBattleDto.getName());
            }

            //TODO: save repo link;


            //close connection
            connection.disconnect();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @NotNull
    private static HttpURLConnection getHttpURLConnection() throws IOException {
        URL url = new URL("https://api.github.com/user/repos");

        //open a HttpURLConnection connection
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        //set POST as request method
        connection.setRequestMethod("POST");

        //set request headers
        connection.setRequestProperty("Authorization", "Bearer " + "GH TOKEN");
        connection.setRequestProperty("X-GitHub-Api-Version", "2022-11-28");
        connection.setRequestProperty("Content-Type", "application/json");

        connection.setDoOutput(true);
        return connection;
    }
}




