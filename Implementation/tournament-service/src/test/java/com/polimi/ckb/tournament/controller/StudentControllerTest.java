package com.polimi.ckb.tournament.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.polimi.ckb.tournament.dto.StudentJoinTournamentDto;
import com.polimi.ckb.tournament.dto.StudentQuitTournamentDto;
import com.polimi.ckb.tournament.entity.Tournament;
import com.polimi.ckb.tournament.service.TournamentService;
import com.polimi.ckb.tournament.service.kafkaProducer.StudentJoinTournamentKafkaProducer;
import com.polimi.ckb.tournament.service.kafkaProducer.StudentQuitTournamentKafkaProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.delete;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(StudentController.class)
public class StudentControllerTest {

    @MockBean
    private TournamentService tournamentService;

    @MockBean
    private StudentJoinTournamentKafkaProducer joinKafkaProducer;

    @MockBean
    private StudentQuitTournamentKafkaProducer quitKafkaProducer;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testJoinTournament() throws Exception {
        StudentJoinTournamentDto joinDto = new StudentJoinTournamentDto();
        // Set properties of joinDto as needed

        Tournament tournament = new Tournament();
        // Set properties of tournament as needed

        given(tournamentService.joinTournament(any(StudentJoinTournamentDto.class))).willReturn(tournament);

        mockMvc.perform(post("/tournament/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(joinDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tournamentId").value(tournament.getTournamentId())); // Adjust as needed
    }

    @Test
    public void testLeaveTournament() throws Exception {
        StudentQuitTournamentDto quitDto = new StudentQuitTournamentDto();
        // Set properties of quitDto as needed

        Tournament tournament = new Tournament();
        // Set properties of tournament as needed

        given(tournamentService.leaveTournament(any(StudentQuitTournamentDto.class))).willReturn(tournament);

        mockMvc.perform(delete("/tournament/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(quitDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tournamentId").value(tournament.getTournamentId())); // Adjust as needed
    }

    // Additional tests...
}
