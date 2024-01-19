package com.polimi.ckb.battleService.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.polimi.ckb.battleService.dto.CreateBattleDto;
import com.polimi.ckb.battleService.dto.DeleteBattleDto;
import com.polimi.ckb.battleService.dto.ErrorResponse;
import com.polimi.ckb.battleService.dto.GetBattleDto;
import com.polimi.ckb.battleService.entity.Battle;
import com.polimi.ckb.battleService.exception.*;
import com.polimi.ckb.battleService.service.BattleService;
import com.polimi.ckb.battleService.service.kafkaProducer.BattleCreationKafkaProducer;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.io.*;
import java.net.HttpURLConnection;
import java.net.URL;
import java.nio.charset.StandardCharsets;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping(path = "/battle")
@Slf4j
public class BattleController {
    private final BattleService battleService;
    private final BattleCreationKafkaProducer kafkaProducer;
    @Value("${github.api.token}")
    private static String gitHubToken;

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

            //If battle name is "TEST" then the caller is a test method (i.e. isTest = true)
            //Otherwise, the caller is a real user (i.e. isTest = false)
            Battle createdBattle;
            if(createBattleDto.getName().equals("TEST"))
                createdBattle = battleService.createBattle(createBattleDto, true);
            else{
                createdBattle = battleService.createBattle(createBattleDto, false);
                if(createGitHubRepository(createBattleDto) == null){
                    //if repository creation fails, delete the battle
                    battleService.deleteBattle(
                            DeleteBattleDto.builder()
                                    .battleId(createdBattle.getBattleId())
                                    .build()
                    );
                    throw new ErrorWhileCreatingRepositoryException(createBattleDto.getName());
                }
            }

            kafkaProducer.sendBattleCreationMessage(createBattleDto);
            log.info("Battle created successfully");
            return ResponseEntity.ok().body(createdBattle);
        } catch (BattleAlreadyExistException | BattleDeadlinesOverlapException | TournamentNotActiveException |
                 EducatorNotAuthorizedException e) {
            log.error("Bad request: {}", e.getMessage());
            return ResponseEntity.badRequest().body(new ErrorResponse(e.getMessage()));
        } catch (JsonProcessingException e) {
            log.error("Error processing JSON: {}", e.getMessage());
            return ResponseEntity.internalServerError().body(new ErrorResponse("Error processing JSON: " + e.getMessage()));
        } catch (ErrorWhileCreatingRepositoryException e) {
            log.error("Error while creating repository {}", e.getMessage());
            return ResponseEntity.internalServerError().body(new ErrorResponse("Error while creating repository: " + e.getMessage()));
        }
    }

    @GetMapping
    public ResponseEntity<Object> getBattle(@RequestBody @Valid GetBattleDto getBattleDto) {
        log.info("Getting battle with message: {" + getBattleDto + "}");
        List<Battle> battles = battleService.getBattlesByTournamentId(getBattleDto);
        log.info("Battle retrieved successfully");
        if(battles.isEmpty())
            return ResponseEntity.noContent().build();
        else
            return ResponseEntity.ok().body(battles);
    }

    private String createGitHubRepository(CreateBattleDto createBattleDto) {
        try {
            final String requestBody = "{\"name\":\"" + createBattleDto.getName() + "\",\"description\":\"" + createBattleDto.getDescription() + "\"}";

            //get connection with GitHub api
            HttpURLConnection connection = getHttpURLConnection();

            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            //send request
            outputStream.write(requestBody.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();

            //check response code
            int responseCode = connection.getResponseCode();
            if(responseCode != HttpURLConnection.HTTP_CREATED){
                throw new ErrorWhileCreatingRepositoryException(createBattleDto.getName());
            }

            //TODO: save repo link;
            //get response
            String repositoryUrl = null;
            try (InputStream inputStream = connection.getInputStream()){
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

                //read response line by line
                StringBuilder response = new StringBuilder();
                String line;
                while ((line = bufferedReader.readLine()) != null) {
                    response.append(line);
                }

                //map the request body to a JsonNode object
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(response.toString());

                //get repository url
                repositoryUrl = rootNode.get("html_url").asText();
                log.info("Repository created successfully at: " + repositoryUrl);
            } catch (Exception e) {
                e.printStackTrace();
            }

            //close connection
            connection.disconnect();
            return repositoryUrl;
        } catch (Exception e) {
            return null;//e.printStackTrace();
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
        connection.setRequestProperty("Authorization", "Bearer " + gitHubToken);
        connection.setRequestProperty("X-GitHub-Api-Version", "2022-11-28");
        connection.setRequestProperty("Content-Type", "application/json");

        connection.setDoOutput(true);
        return connection;
    }
}




