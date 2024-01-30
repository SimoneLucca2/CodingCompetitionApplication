package com.polimi.ckb.battleService.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.polimi.ckb.battleService.dto.CreateBattleDto;
import com.polimi.ckb.battleService.dto.CreatedBattleDto;
import com.polimi.ckb.battleService.dto.NewPushDto;
import com.polimi.ckb.battleService.exception.ErrorWhileCreatingRepositoryException;
import com.polimi.ckb.battleService.service.GitService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.io.FileUtils;
import org.jetbrains.annotations.NotNull;
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
public class GitServiceImpl implements GitService {
    //@Value("${github.api.token}")
    private static final String gitHubToken = "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";

    //@Value("${github.api.username}")
    private final String gitHubUsername = "MarcoF17";
    private final String sonarCloudOrgName = "marcof17";

    //TODO: generate a valid sonarQUBE token
    private final String sonarCloudToken = "0a95732fbb06e15705af12c72052952bdac41525";
    private final String sonarProjectKey = "ckb";
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

        //create the sonar-project.properties file
        //createSonarProjectPropertiesFile(repoName);

        //add the yaml file
        git.add().addFilepattern(".").call();

        //commit changes
        git.commit().setMessage("Add notify-on-push.yaml, build.yaml and sonar-project.properties").call();

        //push changes
        git.push().setRemote("origin").setCredentialsProvider(new UsernamePasswordCredentialsProvider(gitHubUsername, gitHubToken)).call();

        //close the repository
        git.close();

        //delete the temporary repository
        deleteRepository();
    }

    private void copyDirectory(Path source, Path destination) throws IOException {
        FileUtils.copyDirectory(source.toFile(), destination.toFile());
    }

    private void deleteRepository() throws IOException {
        Path localpath = Paths.get("./temp");

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
            HttpURLConnection connection = getHttpURLConnectionGitHub("https://api.github.com/user/repos", "POST");

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
            outputStream.close();
            connection.disconnect();
            return repositoryUrl;
        } catch (Exception e) {
            return null;//e.printStackTrace();
        }
    }

    @Override
    public void createSecrets(final CreatedBattleDto dto, final String sonarCloudProjectToken) throws IOException {
        //get connection with GitHub api
        HttpURLConnection connection = getHttpURLConnectionGitHub("https://api.github.com/repos/" + gitHubUsername + "/" +  dto.getName()  + "/actions/secrets/GIT_TOKEN", "PUT");

        //set request body
        final String requestBody = "{\"encrypted_value\":\"" + "c2VjcmV0" + "\",\"key_id\":\"" + gitHubToken + "\"}";

        DataOutputStream outputStream = new DataOutputStream(connection.getOutputStream());
        //send request
        outputStream.write(requestBody.getBytes(StandardCharsets.UTF_8));
        outputStream.flush();

        //check response code
        int responseCode = connection.getResponseCode();
        if(responseCode != HttpURLConnection.HTTP_CREATED){
            throw new ErrorWhileCreatingRepositoryException(dto.getName());
        }

        //close connection
        outputStream.close();
        connection.disconnect();

        //get connection with GitHub api
        HttpURLConnection connectionBis = getHttpURLConnectionGitHub("https://api.github.com/repos/" + gitHubUsername + "/" +  dto.getName()  + "/actions/secrets/SONAR_TOKEN", "PUT");

        //set request body
        final String requestBodyBis = "{\"encrypted_value\":\"" + "c2VjcmV0" + "\",\"key_id\":\"" + sonarCloudProjectToken + "\"}";

        DataOutputStream outputStreamBis = new DataOutputStream(connectionBis.getOutputStream());
        //send request
        outputStreamBis.write(requestBodyBis.getBytes(StandardCharsets.UTF_8));
        outputStreamBis.flush();

        //check response code
        int responseCodeBis = connectionBis.getResponseCode();
        if(responseCodeBis != HttpURLConnection.HTTP_CREATED){
            throw new ErrorWhileCreatingRepositoryException(dto.getName());
        }

        //close connection
        outputStream.close();
        connection.disconnect();
    }

    @NotNull
    private static HttpURLConnection getHttpURLConnectionGitHub(final String StringUrl, final String method) throws IOException {
        URL url = new URL(StringUrl);

        //open a HttpURLConnection connection
        HttpURLConnection connection = (HttpURLConnection) url.openConnection();

        //set POST as request method
        connection.setRequestMethod(method);

        //set request headers
        connection.setRequestProperty("Authorization", "Bearer " + gitHubToken);
        connection.setRequestProperty("X-GitHub-Api-Version", "2022-11-28");
        connection.setRequestProperty("Content-Type", "application/json");
        connection.setDoOutput(true);

        return connection;
    }

    @Override
    public void calculateTemporaryScore(NewPushDto newPushDto) throws GitAPIException, IOException, InterruptedException {
        //every time the system gets a notification about a new push on the main branch of a group's repo, solution is assigned a temporary score

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
        //processBuilder.environment().put("PATH", "/home/marcolino/Documenti/SonarQube/sonar-scanner-cli-5.0.1.3006-linux/bin:" + System.getenv("PATH"));
        //processBuilder.environment().put("SONAR_RUNNER_HOME", "/home/marcolino/Documenti/SonarQube/sonar-scanner-cli-5.0.1.3006-linux/sonar-scanner-5.0.1.3006-linux/bin/sonar-scanner");
        log.info("Starting analysis with sonar-scanner");
        Process process = processBuilder.start();
        BufferedReader readerOutput = new BufferedReader(new InputStreamReader(process.getInputStream()));
        BufferedReader readerError = new BufferedReader(new InputStreamReader(process.getErrorStream()));

        int exitCode = process.waitFor();
        if(exitCode != 0) //TODO: create an ad hoc exception
            throw new RuntimeException("Error while executing sonar-scanner");

        //Get the temporary score from sonarqube and save it in the database
        //TODO: use this -> GET api/measures/component?component=ckb&metricKeys=bugs,vulnerabilities,code_smells,coverage,duplicated_lines_density&additionalFields=metrics

        //Delete the cloned repository

        //Delete sonarqube project through API
    }

    private void createSonarQubeProject() throws IOException {
        final String sonarCloudURL = "http://localhost:9000/api/projects/create";
        final String encodedProjectName = URLEncoder.encode(sonarProjectKey, StandardCharsets.UTF_8);
        final String encodedProjectKey = URLEncoder.encode(sonarProjectKey, StandardCharsets.UTF_8);
        final String urlWithParams = sonarCloudURL + "?name=" + encodedProjectName + "&project=" + encodedProjectKey;

        final HttpClient client = HttpClient.newHttpClient();
        final HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(urlWithParams))
                .header("Authorization", "Bearer " + "sqa_c4f04e0210ce47711da0af97bfc49a1f251ca9ae")
                //.header("Content-Type", "application/json")
                .POST(HttpRequest.BodyPublishers.noBody())
                .build();

        try {
            final HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());
            log.info(String.valueOf(response.statusCode()));
            log.info(response.body());

            if (response.statusCode() == HttpURLConnection.HTTP_CREATED) {
                log.info("SonarQube project successfully created");
            } else {
                //TODO: create an ad hoc exception
            }
        } catch (InterruptedException e) {
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
        log.info("SSS");

        fileWriter.close();
    }
}
