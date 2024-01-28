package com.polimi.ckb.battleService.service.impl;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.polimi.ckb.battleService.dto.CreateBattleDto;
import com.polimi.ckb.battleService.dto.CreatedBattleDto;
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
    private final String sonarCloudToken = "0a95732fbb06e15705af12c72052952bdac41525";
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
        createSonarProjectPropertiesFile(repoName);

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

    private void createSonarProjectPropertiesFile(final String repoName) throws IOException {
        File file = new File("./temp/sonar-project.properties");

        if(!file.createNewFile())
            throw new IOException("Error while creating sonar-project.properties file");

        FileWriter fileWriter = new FileWriter(file);
        fileWriter.write("sonar.projectKey=" + gitHubUsername + "_" + repoName + "\n");
        fileWriter.write("sonar.organization=" + sonarCloudOrgName + "\n");

        fileWriter.close();
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
    public void createSonarCloudProject(final CreateBattleDto dto) throws IOException {
        String sonarCloudURL = "https://sonarcloud.io/api/projects/create";
        final String projectKey = gitHubUsername + "_" + dto.getName();
        String requestBody = "{\"name\":" + dto.getName() + ", \"organization\":" + sonarCloudOrgName +
                ", \"project\":" + projectKey + "}";

        HttpClient client = HttpClient.newHttpClient();
        HttpRequest request = HttpRequest.newBuilder()
                .uri(URI.create(sonarCloudURL))
                .header("Authorization", "Basic " + sonarCloudToken)
                .POST(HttpRequest.BodyPublishers.ofString(requestBody))
                .build();

        try {
            HttpResponse<String> response = client.send(request, HttpResponse.BodyHandlers.ofString());

            if (response.statusCode() == HttpURLConnection.HTTP_CREATED) {
               log.info("SonarCloud project successfully created");
            } else {
                //TODO: create an ad hoc exception
            }
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }
    }
}
