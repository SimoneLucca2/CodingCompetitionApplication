package com.polimi.ckb.battleService.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.polimi.ckb.BattleTestUtil;
import com.polimi.ckb.battleService.dto.StudentJoinBattleDto;
import com.polimi.ckb.battleService.entity.Battle;
import com.polimi.ckb.battleService.entity.Educator;
import com.polimi.ckb.battleService.service.BattleService;
import com.polimi.ckb.battleService.service.EducatorService;
import com.polimi.ckb.battleService.service.GroupService;
import com.polimi.ckb.battleService.service.StudentService;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BattleControllerTest {
    private MockMvc mockMvc;
    private ObjectMapper objectMapper;

    @Autowired
    public BattleControllerTest(MockMvc mockMvc) {
        this.mockMvc = mockMvc;
        this.objectMapper = new ObjectMapper();
    }

    @Test
    @Transactional
    public void testThatBattleCanBeCreated() throws Exception {
        Battle battle = BattleTestUtil.createTestBattle();
        Educator educator = BattleTestUtil.createTestEducator();
        battle.setCreatorId(educator);
        String battleJson = objectMapper.writeValueAsString(battle);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/battle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(battleJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    @Transactional
    public void testThatOverlappingBattlesCannotBeCreated() throws Exception{
        Battle fisrtBattle = BattleTestUtil.createTestBattle();
        Educator educator = BattleTestUtil.createTestEducator();
        Battle overlappingBattle = BattleTestUtil.createTestOverlappingBattle();
        fisrtBattle.setCreatorId(educator);
        overlappingBattle.setCreatorId(educator);

        String firstBattleJson = objectMapper.writeValueAsString(fisrtBattle);
        String secondBattleJson = objectMapper.writeValueAsString(overlappingBattle);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/battle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(firstBattleJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );

        mockMvc.perform(
                MockMvcRequestBuilders.post("/battle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(secondBattleJson)
        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        );
    }

    @Test
    @Transactional
    public void testThatBattleWithTheSameNameCannotBeCreatedInTheSameTournament() throws Exception {
        Battle firstBattle = BattleTestUtil.createTestBattle();
        Battle secondBattle = BattleTestUtil.createTestBattle();
        Educator educator = BattleTestUtil.createTestEducator();
        firstBattle.setCreatorId(educator);
        secondBattle.setCreatorId(educator);
        secondBattle.setRegistrationDeadline("2024-02-01");
        secondBattle.setSubmissionDeadline("2024-02-10");
        String firstBattleJson = objectMapper.writeValueAsString(firstBattle);
        String secondBattleJson = objectMapper.writeValueAsString(secondBattle);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/battle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(firstBattleJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );

        mockMvc.perform(
                MockMvcRequestBuilders.post("/battle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(secondBattleJson)
        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        );

    }

    @Test
    @Transactional
    public void testThatGroupConstraintsCheckingSystemWorksFine() throws Exception{
        Battle battle = BattleTestUtil.createTestBattle();
        battle.setMinGroupSize(10);
        Educator educator = BattleTestUtil.createTestEducator();
        battle.setCreatorId(educator);
        String battleJson = objectMapper.writeValueAsString(battle);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/battle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(battleJson)
        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        );
    }

    @Test
    @Transactional
    public void testThatDeadlinesAreGivenInTheRightOrder() throws Exception{
        Battle battle = BattleTestUtil.createTestBattle();
        Educator educator = BattleTestUtil.createTestEducator();
        battle.setCreatorId(educator);
        battle.setSubmissionDeadline("2024-01-01");
        String battleJson = objectMapper.writeValueAsString(battle);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/battle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(battleJson)
        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        );
    }

    @Test
    @Transactional
    public void testThatStudentCanJoinABattleBeforeTheDeadlineAndThenQuitIt() throws Exception{
        Battle battle = BattleTestUtil.createTestBattle();
        Educator educator = BattleTestUtil.createTestEducator();
        battle.setCreatorId(educator);
        String battleJson = objectMapper.writeValueAsString(battle);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/battle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(battleJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );

        StudentJoinBattleDto dto = StudentJoinBattleDto.builder()
                .battleId(47L)  //TODO: GET /battle in order to get the id of a battle given its name
                .studentId(2L)
                .build();

        String studentJson = objectMapper.writeValueAsString(dto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/battle/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(studentJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );

        mockMvc.perform(
                MockMvcRequestBuilders.delete("/battle/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(studentJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }
}