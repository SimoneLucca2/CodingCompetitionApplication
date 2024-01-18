package com.polimi.ckb.battleService.controller;

import com.polimi.ckb.battleService.dto.NewPushDto;
import com.polimi.ckb.battleService.service.BattleService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping(path = "/battle/score")
@Slf4j
@AllArgsConstructor
public class ScoreController {
    private final BattleService battleService;

    @PostMapping
    public void checkNewPushOnMainBranch(@RequestBody NewPushDto newPushDto){
        log.info("A new push on the main branch has been performed from group " /*+ group.getGroupId()*/);
        battleService.calculateTemporaryScore(newPushDto);
    }
}
