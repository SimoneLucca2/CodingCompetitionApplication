package com.polimi.ckb.battleService.service.impl;

import com.polimi.ckb.battleService.service.GitService;
import org.apache.commons.io.FileUtils;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.eclipse.jgit.api.Git;
import org.eclipse.jgit.api.errors.GitAPIException;
import org.eclipse.jgit.transport.UsernamePasswordCredentialsProvider;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

@Service
public class GitServiceImpl implements GitService {
    //@Value("${github.api.token}")
    private final String gitHubToken = "";

    //@Value("${github.api.username}")
    private final String gitHubUsername = "MarcoF17";
    @Override
    public void uploadYamlFileForNotifications(final String repositoryUrl) throws GitAPIException, IOException {
        //clone the repository
        Git git = Git.cloneRepository()
                .setURI(repositoryUrl)
                .setCredentialsProvider(new UsernamePasswordCredentialsProvider(gitHubUsername, gitHubToken))
                .setDirectory(new File("./temp"))
                .call();

        //copy the directory into the repository
        final Path sourceDirectory = Paths.get("./src/main/resources/ghutils");
        final Path destinationDirectory = Paths.get("./temp");
        copyDirectory(sourceDirectory, destinationDirectory);

        //add the yaml file
        git.add().addFilepattern(".").call();

        //commit changes
        git.commit().setMessage("Add notify-on-push.yaml daiii").call();

        //push changes
        git.push().setRemote("origin").setCredentialsProvider(new UsernamePasswordCredentialsProvider(gitHubUsername, gitHubToken)).call();

        //close the repository
        git.close();

        //delete the temporary repository
        deleteRepository(repositoryUrl);
    }

    private void copyDirectory(Path source, Path destination) throws IOException {
        FileUtils.copyDirectory(source.toFile(), destination.toFile());
    }

    private void deleteRepository(final String repositoryUrl) throws IOException {
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
}
