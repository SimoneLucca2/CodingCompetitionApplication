package com.polimi.ckb.battleService.service;

import com.polimi.ckb.battleService.dto.CreateBattleDto;
import com.polimi.ckb.battleService.dto.CreatedBattleDto;
import com.polimi.ckb.battleService.dto.NewPushDto;
import org.eclipse.jgit.api.errors.GitAPIException;

import java.io.IOException;

public interface GitService {
    void uploadSetupFiles(final String repositoryUrl, final String repoName) throws GitAPIException, IOException;

    String createGitHubRepository(CreateBattleDto createBattleDto);

    void calculateTemporaryScore(NewPushDto newPushDto) throws GitAPIException, IOException, InterruptedException;
}
