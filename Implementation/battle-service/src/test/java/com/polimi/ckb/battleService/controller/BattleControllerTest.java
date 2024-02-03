package com.polimi.ckb.battleService.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.polimi.ckb.BattleTestUtil;
import com.polimi.ckb.battleService.config.BattleStatus;
import com.polimi.ckb.battleService.config.TournamentStatus;
import com.polimi.ckb.battleService.dto.*;
import com.polimi.ckb.battleService.entity.Battle;
import com.polimi.ckb.battleService.entity.Educator;
import com.polimi.ckb.battleService.entity.Student;
import com.polimi.ckb.battleService.entity.StudentGroup;
import com.polimi.ckb.battleService.repository.BattleRepository;
import com.polimi.ckb.battleService.repository.EducatorRepository;
import com.polimi.ckb.battleService.repository.GroupRepository;
import com.polimi.ckb.battleService.repository.StudentRepository;
import com.polimi.ckb.battleService.service.BattleService;
import com.polimi.ckb.battleService.service.GitService;
import com.polimi.ckb.battleService.service.impl.BattleServiceImpl;
import jakarta.transaction.Transactional;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.ArrayList;
import java.util.List;

import static org.mockito.Mockito.when;

@SpringBootTest
@ExtendWith(SpringExtension.class)
@DirtiesContext(classMode = DirtiesContext.ClassMode.AFTER_EACH_TEST_METHOD)
@AutoConfigureMockMvc
public class BattleControllerTest {
    private final MockMvc mockMvc;
    private final ObjectMapper objectMapper;
    @Mock
    private final BattleServiceImpl battleService;
    private final BattleRepository battleRepository;
    private final GroupRepository groupRepository;
    private final StudentRepository studentRepository;
    private final EducatorRepository educatorRepository;

    // Mocks for DTOs
    private CreateBattleDto createBattleDto;
    private TournamentDto tournamentDto;

    private final GitService gitService;

    @Autowired
    public BattleControllerTest(MockMvc mockMvc, BattleServiceImpl battleService, BattleRepository battleRepository,
                                GroupRepository groupRepository, StudentRepository studentRepository,
                                EducatorRepository educatorRepository, GitService gitService) {
        this.mockMvc = mockMvc;
        this.gitService = gitService;
        this.objectMapper = new ObjectMapper();
        this.battleService = battleService;
        this.battleRepository = battleRepository;
        this.groupRepository = groupRepository;
        this.studentRepository = studentRepository;
        this.educatorRepository = educatorRepository;
    }

    @BeforeEach
    void setUp() throws JsonProcessingException {
        // Initialize your DTOs with test data
        createBattleDto = new CreateBattleDto();
        tournamentDto = new TournamentDto();

        // Setup test data for CreateBattleDto
        createBattleDto.setTournamentId(20L);
        createBattleDto.setCreatorId(12L);

        // Setup test data for TournamentDto
        tournamentDto.setStatus(TournamentStatus.ACTIVE);
        tournamentDto.setCreatorId(12L);
        tournamentDto.setOrganizerIds(List.of(290L, 321L));

        // Configure mock behavior for tournamentService
        when(battleService.checkTournamentStatus(createBattleDto.getTournamentId())).thenReturn(tournamentDto);
    }

    @Test
    @Transactional
    @DisplayName("Test successful battle creation")
    public void testThatBattleCanBeCreatedIfEverythingIsOk() throws Exception {
        CreateBattleDto battle = BattleTestUtil.createTestBattleDto();
        Educator educator = Educator.builder()
                .educatorId(1L)
                .battles(new ArrayList<>())
                .build();
        educatorRepository.save(educator);
        battle.setCreatorId(educator.getEducatorId());
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
    @DisplayName("No overlapping battles can be created")
    public void testThatOverlappingBattlesCannotBeCreated() throws Exception{
        Educator educator = Educator.builder()
                .educatorId(1L)
                .build();
        educatorRepository.save(educator);
        Battle overlappingBattle = BattleTestUtil.createTestOverlappingBattle();
        overlappingBattle.setCreator(educator);

        String overlappingBattleJson = objectMapper.writeValueAsString(overlappingBattle);

        CreateBattleDto battleDto = BattleTestUtil.createTestBattleDto();
        battleDto.setCreatorId(educator.getEducatorId());
        battleService.createBattle(battleDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/battle")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(overlappingBattleJson)
        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        );
    }

    @Test
    @Transactional
    @DisplayName("Battles don't share names within a tournament")
    public void testThatBattleWithTheSameNameCannotBeCreatedInTheSameTournament() throws Exception {
        Battle secondBattle = BattleTestUtil.createTestBattle();
        Educator educator = Educator.builder()
                .educatorId(1L)
                .build();
        educatorRepository.save(educator);
        secondBattle.setCreator(educator);
        secondBattle.setRegistrationDeadline("2024-02-01");
        secondBattle.setSubmissionDeadline("2024-02-10");
        String secondBattleJson = objectMapper.writeValueAsString(secondBattle);

        CreateBattleDto battleDto = BattleTestUtil.createTestBattleDto();
        battleDto.setCreatorId(educator.getEducatorId());
        battleService.createBattle(battleDto);

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
    @DisplayName("maxGroupSize > minGroupSize")
    public void testThatGroupConstraintsCheckingSystemWorksFine() throws Exception{
        CreateBattleDto battle = BattleTestUtil.createTestBattleDto();
        battle.setMinGroupSize(10);
        Educator educator = Educator.builder()
                .educatorId(1L)
                .build();
        educatorRepository.save(educator);
        battle.setCreatorId(educator.getEducatorId());
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
    @DisplayName("registration-deadline < submission-deadline")
    public void testThatDeadlinesAreGivenInTheRightOrder() throws Exception{
        CreateBattleDto battle = BattleTestUtil.createTestBattleDto();
        Educator educator = Educator.builder()
                .educatorId(1L)
                .build();
        educatorRepository.save(educator);
        battle.setCreatorId(educator.getEducatorId());
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
    @DisplayName("Student successfully joins a battle")
    public void testThatStudentCanJoinABattleBeforeTheDeadline() throws Exception{
        Educator educator = Educator.builder()
                .educatorId(1L)
                .build();
        educatorRepository.save(educator);
        studentRepository.save(Student.builder()
                .studentId(2L)
                .build());

        CreateBattleDto battleDto = BattleTestUtil.createTestBattleDto();
        battleDto.setCreatorId(educator.getEducatorId());
        Battle battle = battleService.createBattle(battleDto);

        StudentJoinBattleDto dto = BattleTestUtil.createTestStudentJoinBattleDto();
        dto.setBattleId(battle.getBattleId());

        String studentJson = objectMapper.writeValueAsString(dto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/battle/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(studentJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    @Transactional
    @DisplayName("No joining after registration-deadline")
    public void testThatStudentCannotJoinABattleAfterRegistrationDeadlineHasExpired() throws Exception{
        Educator educator = Educator.builder()
                .educatorId(1L)
                .build();
        educatorRepository.save(educator);
        studentRepository.save(Student.builder()
                .studentId(2L)
                .build());

        CreateBattleDto battleDto = BattleTestUtil.createTestBattleDto();
        battleDto.setCreatorId(educator.getEducatorId());
        Battle battle = battleService.createBattle(battleDto);
        battle.setStatus(BattleStatus.BATTLE);
        battleRepository.save(battle);

        StudentJoinBattleDto dto = BattleTestUtil.createTestStudentJoinBattleDto();
        dto.setBattleId(battle.getBattleId());

        String studentJson =  objectMapper.writeValueAsString(dto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/battle/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(studentJson)
        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        );
    }

    @Test
    @Transactional
    @DisplayName("Student successfully leaves a battle (status: PRE_BATTLE)")
    public void testThatStudentCanLeaveABattleIfRegisteredAndStateIsPreBattle() throws Exception {
        Educator educator = Educator.builder()
                .educatorId(1L)
                .build();
        educatorRepository.save(educator);
        studentRepository.save(Student.builder()
                .studentId(2L)
                .build());

        CreateBattleDto battleDto = BattleTestUtil.createTestBattleDto();
        battleDto.setCreatorId(educator.getEducatorId());
        Battle battle = battleService.createBattle(battleDto);
        battleRepository.save(battle);

        StudentJoinBattleDto dto = BattleTestUtil.createTestStudentJoinBattleDto();
        dto.setBattleId(battle.getBattleId());
        battleService.joinBattle(dto);

        StudentLeaveBattleDto leaveDto = BattleTestUtil.createTestStudentQuitBattleDto();
        leaveDto.setBattleId(battle.getBattleId());

        String studentJson =  objectMapper.writeValueAsString(leaveDto);
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/battle/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(studentJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    @Transactional
    @DisplayName("Student successfully leaves a battle (status: BATTLE)")
    public void testThatStudentCanLeaveABattleIfRegisteredAndStateIsBattle() throws Exception {
        Educator educator = Educator.builder()
                .educatorId(1L)
                .build();
        educatorRepository.save(educator);
        studentRepository.save(Student.builder()
                .studentId(2L)
                .build());

        CreateBattleDto battleDto = BattleTestUtil.createTestBattleDto();
        battleDto.setCreatorId(educator.getEducatorId());
        Battle battle = battleService.createBattle(battleDto);

        StudentJoinBattleDto dto = BattleTestUtil.createTestStudentJoinBattleDto();
        dto.setBattleId(battle.getBattleId());
        battleService.joinBattle(dto);
        battle.setStatus(BattleStatus.BATTLE);
        battleRepository.save(battle);

        StudentLeaveBattleDto leaveDto = BattleTestUtil.createTestStudentQuitBattleDto();
        leaveDto.setBattleId(battle.getBattleId());

        String studentJson =  objectMapper.writeValueAsString(leaveDto);
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/battle/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(studentJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    @Transactional
    @DisplayName("Student unsuccessfully leaves a battle (status: CONSOLIDATION)")
    public void testThatStudentCannotLeaveABattleIfRegisteredAndStateIsConsolidation() throws Exception {
        Educator educator = Educator.builder()
                .educatorId(1L)
                .build();
        educatorRepository.save(educator);
        studentRepository.save(Student.builder()
                .studentId(2L)
                .build());

        CreateBattleDto battleDto = BattleTestUtil.createTestBattleDto();
        battleDto.setCreatorId(educator.getEducatorId());
        Battle battle = battleService.createBattle(battleDto);

        StudentJoinBattleDto dto = BattleTestUtil.createTestStudentJoinBattleDto();
        dto.setBattleId(battle.getBattleId());
        battleService.joinBattle(dto);
        battle.setStatus(BattleStatus.CONSOLIDATION);
        battleRepository.save(battle);

        StudentLeaveBattleDto leaveDto = BattleTestUtil.createTestStudentQuitBattleDto();
        leaveDto.setBattleId(battle.getBattleId());

        String studentJson =  objectMapper.writeValueAsString(leaveDto);
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/battle/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(studentJson)
        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        );
    }

    @Test
    @Transactional
    @DisplayName("Student unsuccessfully leaves a battle (status: CLOSED)")
    public void testThatStudentCannotLeaveABattleIfRegisteredAndStateIsClosed() throws Exception {
        Educator educator = Educator.builder()
                .educatorId(1L)
                .build();
        educatorRepository.save(educator);
        studentRepository.save(Student.builder()
                .studentId(2L)
                .build());

        CreateBattleDto battleDto = BattleTestUtil.createTestBattleDto();
        battleDto.setCreatorId(educator.getEducatorId());
        Battle battle = battleService.createBattle(battleDto);

        StudentJoinBattleDto dto = BattleTestUtil.createTestStudentJoinBattleDto();
        dto.setBattleId(battle.getBattleId());
        battleService.joinBattle(dto);
        battle.setStatus(BattleStatus.CLOSED);
        battleRepository.save(battle);

        StudentLeaveBattleDto leaveDto = BattleTestUtil.createTestStudentQuitBattleDto();
        leaveDto.setBattleId(battle.getBattleId());

        String studentJson =  objectMapper.writeValueAsString(leaveDto);
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/battle/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(studentJson)
        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        );
    }

    @Test
    @Transactional
    @DisplayName("Student cannot leave a battle if not registered")
    public void testThatStudentCannotLeaveABattleIfNotRegistered() throws Exception {
        Educator educator = Educator.builder()
                .educatorId(1L)
                .build();
        educatorRepository.save(educator);
        studentRepository.save(Student.builder()
                .studentId(2L)
                .build());

        CreateBattleDto battleDto = BattleTestUtil.createTestBattleDto();
        battleDto.setCreatorId(educator.getEducatorId());
        Battle battle = battleService.createBattle(battleDto);

        StudentLeaveBattleDto leaveDto = BattleTestUtil.createTestStudentQuitBattleDto();
        leaveDto.setBattleId(battle.getBattleId());

        String studentJson =  objectMapper.writeValueAsString(leaveDto);
        mockMvc.perform(
                MockMvcRequestBuilders.delete("/battle/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(studentJson)
        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        );
    }

    @Test
    @Transactional
    @DisplayName("Student successfully invites another student in th group")
    public void testThatStudentsCanInviteOtherStudentsInGroupIfBattleNotStarted() throws Exception {
        Educator educator = Educator.builder()
                .educatorId(1L)
                .build();
        educatorRepository.save(educator);
        studentRepository.save(Student.builder()
                .studentId(2L)
                .build()
        );

        CreateBattleDto battleDto = BattleTestUtil.createTestBattleDto();
        battleDto.setCreatorId(educator.getEducatorId());
        Battle battle = battleService.createBattle(battleDto);

        StudentJoinBattleDto firstDto = BattleTestUtil.createTestStudentJoinBattleDto();
        firstDto.setBattleId(battle.getBattleId());
        StudentGroup group = battleService.joinBattle(firstDto);

        studentRepository.save(Student.builder()
                .studentId(10L)
                .build()
        );

        StudentJoinBattleDto secondDto = BattleTestUtil.createTestStudentJoinBattleDto();
        secondDto.setStudentId(10L);
        secondDto.setBattleId(battle.getBattleId());
        battleService.joinBattle(secondDto);

        StudentInvitesToGroupDto inviteDto = StudentInvitesToGroupDto.builder()
                .requesterId(firstDto.getStudentId())
                .invitedId(secondDto.getStudentId())
                .groupId(group.getGroupId())
                .build();

        String inviteJson = objectMapper.writeValueAsString(inviteDto);

        mockMvc.perform(
                MockMvcRequestBuilders.post("/battle/group")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(inviteJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    @Transactional
    @DisplayName("Student successfully joins a group")
    public void testThatStudentCanJoinAnotherGroupIfBattleStatusIsPreBattle() throws Exception {
        Educator educator = Educator.builder()
                .educatorId(1L)
                .build();
        educatorRepository.save(educator);
        studentRepository.save(Student.builder()
                .studentId(2L)
                .build()
        );

        CreateBattleDto battleDto = BattleTestUtil.createTestBattleDto();
        battleDto.setCreatorId(educator.getEducatorId());
        Battle battle = battleService.createBattle(battleDto);

        StudentJoinBattleDto firstDto = BattleTestUtil.createTestStudentJoinBattleDto();
        firstDto.setBattleId(battle.getBattleId());
        StudentGroup group = battleService.joinBattle(firstDto);

        studentRepository.save(Student.builder()
                .studentId(10L)
                .build()
        );

        StudentJoinBattleDto secondDto = BattleTestUtil.createTestStudentJoinBattleDto();
        secondDto.setStudentId(10L);
        secondDto.setBattleId(battle.getBattleId());
        battleService.joinBattle(secondDto);

        StudentJoinsGroupDto groupDto = StudentJoinsGroupDto.builder()
                .studentId(secondDto.getStudentId())
                .groupId(group.getGroupId())
                .build();


        String joinJson = objectMapper.writeValueAsString(groupDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/battle/group")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(joinJson)
        ).andExpect(
                MockMvcResultMatchers.status().isOk()
        );
    }

    @Test
    @Transactional
    @DisplayName("Student cannot change group if the battle has already started")
    public void testThatStudentCannotJoinAnotherGroupIfBattleStatusIsNotPreBattle() throws Exception {
        Educator educator = Educator.builder()
                .educatorId(1L)
                .build();
        educatorRepository.save(educator);
        studentRepository.save(Student.builder()
                .studentId(2L)
                .build()
        );

        CreateBattleDto battleDto = BattleTestUtil.createTestBattleDto();
        battleDto.setCreatorId(educator.getEducatorId());
        Battle battle = battleService.createBattle(battleDto);

        StudentJoinBattleDto firstDto = BattleTestUtil.createTestStudentJoinBattleDto();
        firstDto.setBattleId(battle.getBattleId());
        StudentGroup group = battleService.joinBattle(firstDto);

        studentRepository.save(Student.builder()
                .studentId(10L)
                .build()
        );

        StudentJoinBattleDto secondDto = BattleTestUtil.createTestStudentJoinBattleDto();
        secondDto.setStudentId(10L);
        secondDto.setBattleId(battle.getBattleId());
        battleService.joinBattle(secondDto);

        battle.setStatus(BattleStatus.BATTLE);
        battleRepository.save(battle);

        StudentJoinsGroupDto groupDto = StudentJoinsGroupDto.builder()
                .studentId(secondDto.getStudentId())
                .groupId(group.getGroupId())
                .build();


        String joinJson = objectMapper.writeValueAsString(groupDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/battle/group")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(joinJson)
        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        );
    }

    @Test
    @Transactional
    @DisplayName("Student cannot join a full group")
    public void testThatStudentCannotJoinAnotherGroupIfItIsFull() throws Exception {
        Educator educator = Educator.builder()
                .educatorId(1L)
                .build();
        educatorRepository.save(educator);
        studentRepository.save(Student.builder()
                .studentId(2L)
                .build()
        );

        CreateBattleDto battleDto = BattleTestUtil.createTestBattleDto();
        battleDto.setCreatorId(educator.getEducatorId());
        battleDto.setMinGroupSize(1);
        battleDto.setMaxGroupSize(1);
        Battle battle = battleService.createBattle(battleDto);

        StudentJoinBattleDto firstDto = BattleTestUtil.createTestStudentJoinBattleDto();
        firstDto.setBattleId(battle.getBattleId());
        StudentGroup group = battleService.joinBattle(firstDto);

        studentRepository.save(Student.builder()
                .studentId(10L)
                .build()
        );

        StudentJoinBattleDto secondDto = BattleTestUtil.createTestStudentJoinBattleDto();
        secondDto.setStudentId(10L);
        secondDto.setBattleId(battle.getBattleId());
        battleService.joinBattle(secondDto);

        StudentJoinsGroupDto groupDto = StudentJoinsGroupDto.builder()
                .studentId(secondDto.getStudentId())
                .groupId(group.getGroupId())
                .build();


        String joinJson = objectMapper.writeValueAsString(groupDto);

        mockMvc.perform(
                MockMvcRequestBuilders.put("/battle/group")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(joinJson)
        ).andExpect(
                MockMvcResultMatchers.status().isBadRequest()
        );
    }
}