package com.polimi.ckb.tournament.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.polimi.ckb.tournament.config.TournamentStatus;
import com.polimi.ckb.tournament.dto.CreateTournamentDto;
import com.polimi.ckb.tournament.entity.Tournament;
import com.polimi.ckb.tournament.service.TournamentService;
import com.polimi.ckb.tournament.service.kafkaProducer.TournamentCreationKafkaProducer;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import java.util.Arrays;
import java.util.List;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TournamentController.class)
public class TournamentControllerTest {

    @MockBean
    private TournamentService tournamentService;

    @MockBean
    private TournamentCreationKafkaProducer kafkaProducer;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testCreateTournament() throws Exception {
        CreateTournamentDto createDto = new CreateTournamentDto();
        createDto.setRegistrationDeadline("2321-01-01");
        createDto.setStatus(TournamentStatus.PREPARATION);
        createDto.setCreatorId(1L);
        createDto.setName("test");

        Tournament tournament = new Tournament();
        tournament.setTournamentId(1L);
        tournament.setCreatorId(1L);
        tournament.setStatus(TournamentStatus.PREPARATION);
        tournament.setName("test");

        given(tournamentService.saveTournament(any())).willReturn(tournament);

        mockMvc.perform(post("/tournament")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(createDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tournamentId").value(tournament.getTournamentId()));
    }

    @Test
    public void testGetAllTournaments() throws Exception {
        Tournament tournament1 = new Tournament(); // Set properties
        Tournament tournament2 = new Tournament(); // Set properties
        List<Tournament> allTournaments = Arrays.asList(tournament1, tournament2);

        given(tournamentService.getAllTournaments()).willReturn(allTournaments);

        mockMvc.perform(get("/tournament/all"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(allTournaments.size()));
    }

    @Test
    public void testGetTournament() throws Exception {
        Long tournamentId = 1L;
        Tournament tournament = new Tournament();
        tournament.setTournamentId(tournamentId);

        given(tournamentService.getTournament(tournamentId)).willReturn(tournament);

        mockMvc.perform(get("/tournament/{tournamentId}", tournamentId))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tournamentId").value(tournamentId));
    }
}
