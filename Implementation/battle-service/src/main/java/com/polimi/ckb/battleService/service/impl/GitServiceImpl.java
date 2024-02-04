package com.polimi.ckb.battleService.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.polimi.ckb.battleService.config.BattleStatus;
import com.polimi.ckb.battleService.dto.CreateBattleDto;
import com.polimi.ckb.battleService.dto.NewPushDto;
import com.polimi.ckb.battleService.entity.Battle;
import com.polimi.ckb.battleService.entity.StudentGroup;
import com.polimi.ckb.battleService.exception.*;
import com.polimi.ckb.battleService.repository.GroupRepository;
import com.polimi.ckb.battleService.service.GitService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.*;
import java.net.HttpURLConnection;
import java.net.URI;
import java.net.URL;
import java.net.URLEncoder;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
@Slf4j
@RequiredArgsConstructor
public class GitServiceImpl implements GitService {

    //@Value("${github.api.token}")
    private static String gitHubToken = "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";
    @Value("${github.api.username}")
    private String gitHubUsername = "MarcoF17";

    @Value("${sonar.token}")
    private String sonarToken;
    @Value("${sonarqube.url}")
    private String sonarqubeUrl;
    @Value("${sonarcloud.token}")
    private String sonarCloudToken;

    private final String sonarProjectKey = "ckb";
    private final GroupRepository groupRepository;
    @Override
    public void uploadSetupFiles(final String repositoryUrl, final String repoName) throws GitAPIException, IOException {
        //clone the repository
        Git git = Git.cloneRepository()
                .setURI(repositoryUrl)
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider(gitHubUsername, gitHubToken))
                .setDirectory(new File("./temp"))
                .call();

        //copy the ghutils directory into the repository
        final Path sourceDirectory = Paths.get("./src/main/resources/ghutils");
        final Path destinationDirectory = Paths.get("./temp");
        copyDirectory(sourceDirectory, destinationDirectory);

        //add the yaml file
        git.add().addFilepattern(".").call();

        //commit changes
        git.commit().setMessage("Add notify-on-push.yaml").call();

        //push changes
        git.push().setRemote("origin").setCredentialsProvider(new UsernamePasswordCredentialsProvider(gitHubUsername, gitHubToken)).call();

        //close the repository
        git.close();

        //delete the temporary repository
        deleteRepository("./temp");
    }

    private void copyDirectory(Path source, Path destination) throws IOException {
        FileUtils.copyDirectory(source.toFile(), destination.toFile());
    }

    private void deleteRepository(final String directoryPath) throws IOException {
        Path localpath = Paths.get(directoryPath);

        if (Files.exists(localpath)) {
            Files.walk(localpath)
                    .sorted((a, b) -> -a.compareTo(b))
                    .forEach(path -> {
                        try {
                            Files.deleteIfExists(path);
                        } catch (IOException e) {
                            e.printStackTrace();
                        }
                    });
        }
    }

    @Override
    public String createGitHubRepository(CreateBattleDto createBattleDto) {
        try {
            log.info("Creating new GitHub repository named {}", createBattleDto.getName());
            final String requestBody = "{\"name\":\"" + createBattleDto.getName() + "\",\"description\":\"" + createBattleDto.getDescription() + "\"}";

            //get connection with GitHub api
            HttpURLConnection connection = getHttpURLConnectionGitHub();

            DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
            //send request
            outputStream.write(requestBody.getBytes(StandardCharsets.UTF_8));
            outputStream.flush();

            //check response code
            int responseCode = connection.getResponseCode();
            if(responseCode != HttpURLConnection.HTTP_CREATED){
                throw new ErrorWhileCreatingRepositoryException(createBattleDto.getName());
            }

            //get response
            String repositoryUrl = null;
            try (InputStream inputStream = connection.getInputStream()){
                JsonNode rootNode = getJsonNode(inputStream);

                //get repository url
                repositoryUrl = rootNode.get("html_url").asText();
                log.info("Repository created successfully at: " + repositoryUrl);
            } catch (Exception e) {
                throw new ErrorWhileCreatingRepositoryException("1");
            }

            //close connection
            outputStream.close();
            connection.disconnect();
            return repositoryUrl;
        } catch (IOException e) {
            throw new ErrorWhileCreatingRepositoryException("2");
        }
    }

    private static JsonNode getJsonNode(InputStream inputStream) throws IOException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream));

        //read response line by line
        StringBuilder response = new StringBuilder();
        String line;
        while ((line = bufferedReader.readLine()) != null) {
            response.append(line);
        }

        //map the request body to a JsonNode object
        ObjectMapper objectMapper = new ObjectMapper();
        return objectMapper.readTree(response.toString());
    }

    @NotNull
    private static HttpURLConnection getHttpURLConnectionGitHub() throws IOException {
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

    @Override
    public void calculateTemporaryScore(NewPushDto newPushDto) throws GitAPIException, IOException, InterruptedException, GroupDoesNotExistsException, ErrorWhileExecutingScannerException, CannotEvaluateGroupSolutionException{
        //every time the system gets a notification about a new push on the main branch of a group's repo, solution is assigned a temporary score
        //Only if battle status is BATTLE
        StudentGroup g = groupRepository.findByClonedRepositoryLink(newPushDto.getRepositoryUrl());
        if(g == null)
            throw new GroupDoesNotExistsException();

        Battle battle = g.getBattle();
        if(!battle.getStatus().equals(BattleStatus.BATTLE)){
            throw new CannotEvaluateGroupSolutionException();
        }

        //TODO: verify github token
        Git git = Git.cloneRepository()
                .setURI(newPushDto.getRepositoryUrl())
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider(newPushDto.getGithubName(), newPushDto.getGithubToken()))
                .setDirectory(new File("./analysis"))
                .call();


        createSonarProjectPropertiesFile();
        createSonarQubeProject();

        //execute sonar-scanner script
        final String scriptPath = "./src/main/resources/sonar-scanner.sh";
        ProcessBuilder processBuilder = new ProcessBuilder("/bin/bash", "-c", scriptPath);
        log.info("Starting analysis with sonar-scanner");
        Process process = processBuilder.start();

        int exitCode = process.waitFor();
        if(exitCode != 0)
            throw new ErrorWhileExecutingScannerException();

        //Get the temporary score from sonarqube and save it in the database
        final String encodedComponent = URLEncoder.encode("ckb", StandardCharsets.UTF_8);
        final String encodedMetricKeys = URLEncoder.encode("bugs,vulnerabilities,code_smells,coverage,duplicated_lines_density", StandardCharsets.UTF_8);
        final String encodedUrl = sonarqubeUrl + "/api/measures/component?component=" + encodedComponent + "&metricKeys=" + encodedMetricKeys + "&additionalFields=metrics";

        final HttpClient client = HttpClient.newHttpClient();
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(encodedUrl))
                .header("Authorization", "Bearer " + sonarToken)    //USER-TOKEN
                .GET()
                .build();

        try {
            final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            //log.info(String.valueOf(response.statusCode()));
            //log.info(response.body());

            if (response.statusCode() == HttpURLConnection.HTTP_OK) {
                log.info("SonarQube project successfully created");
                ObjectMapper objectMapper = new ObjectMapper();
                JsonNode rootNode = objectMapper.readTree(response.toString());
                float score = applyFunction(rootNode);

                StudentGroup group = groupRepository.findByClonedRepositoryLink(newPushDto.getRepositoryUrl());
                group.setScore(score);
                groupRepository.save(group);

                log.info("New score: " + score + "for group: " + group.getGroupId());
            } else {
                throw new ErrorWhileExecutingScannerException();
            }
        } catch (InterruptedException e) {
            throw new ErrorWhileExecutingScannerException();
        }

        //Delete the cloned repository
        deleteRepository("./analysis");

        //Delete sonarqube project through API
    }

    private float applyFunction(JsonNode jsonNode){
        JsonNode componentNode = jsonNode.get("component");
        JsonNode measuresNode = componentNode.get("measures");
        log.info(measuresNode.toString());
        float num = 0;
        for(JsonNode measureNode : measuresNode){
            if(measureNode.get("metric").toString().equals("\"bugs\"") || measureNode.get("metric").toString().equals("\"code_smells\"") || measureNode.get("metric").toString().equals("\"vulnerabilities\"")){
                num += (100 - Integer.min(measureNode.get("value").asInt(), 100));
            } else if(measureNode.get("metric").toString().equals("\"coverage\"")){
                num += measureNode.get("value").asInt();
            } else if(measureNode.get("metric").toString().equals("\"duplicated_lines_density\"")){
                num += (100 - measureNode.get("value").asInt());
            }
        }
        return num/5;
    }

    private void createSonarQubeProject() {
        final String sonarCloudURL = sonarqubeUrl + "/api/projects/create";
        final String encodedProjectName = URLEncoder.encode(sonarProjectKey, StandardCharsets.UTF_8);
        final String encodedProjectKey = URLEncoder.encode(sonarProjectKey, StandardCharsets.UTF_8);
        final String urlWithParams = sonarCloudURL + "?name=" + encodedProjectName + "&project=" + encodedProjectKey;

        final HttpClient client = HttpClient.newHttpClient();
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlWithParams))
                //.timeout(Duration.ofSeconds(10))
                .header("Authorization", "Bearer " + sonarToken)        //USER_TOKEN
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        HttpResponse<String> response = null;
        try {
            response = client.send(request, HttpResponse.BodyHandlers.ofString());
            //log.info(String.valueOf(response.statusCode()));
            //log.info(response.body());

            if (response.statusCode() == HttpURLConnection.HTTP_CREATED) {
                log.info("SonarQube project successfully created");
            } else {
                throw new ErrorWhileCreatingSonarQubeProjectException();
            }
        } catch (IOException | InterruptedException e) {
            //throw new ErrorWhileCreatingSonarQubeProjectException();
            //throw new GroupDoesNotExistsException();
            log.error("Error while creating sonarqube project" + e);
            //log.error("CODE: " + response.statusCode());
            throw new RuntimeException(e);
        }
    }

    private void createSonarProjectPropertiesFile() throws IOException {
        File file = new File("./analysis/sonar-project.properties");

        if(!file.createNewFile())
            throw new IOException("Error while creating sonar-project.properties file");

        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write("sonar.projectKey=" + sonarProjectKey + "\n");
        fileWriter.write("sonar.name=" + sonarProjectKey + "\n");

        fileWriter.close();
    }
}
