package com.polimi.ckb.battleService.service;

import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.IOException;

public interface GitService {
    void uploadYamlFileForNotifications(final String repositoryUrl) throws GitAPIException, IOException;
}
