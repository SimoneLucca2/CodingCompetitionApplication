package com.polimi.ckb.tournament.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.polimi.ckb.tournament.config.TournamentStatus;
import com.polimi.ckb.tournament.dto.AddEducatorDto;
import com.polimi.ckb.tournament.entity.Tournament;
import com.polimi.ckb.tournament.service.EducatorService;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(EducatorController.class)
public class EducatorControllerTest {

    @MockBean
    private EducatorService educatorService;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testAddEducator() throws Exception {
        AddEducatorDto addEducatorDto = new AddEducatorDto();
        addEducatorDto.setRequesterId(1L);
        addEducatorDto.setEducatorId(2L);
        addEducatorDto.setTournamentId(1L);

        Tournament tournament = new Tournament();
        tournament.setTournamentId(1L);
        tournament.setCreatorId(1L);
        tournament.setStatus(TournamentStatus.PREPARATION);

        given(educatorService.addEducatorToTournament(any(AddEducatorDto.class))).willReturn(tournament);

        mockMvc.perform(post("/tournament/educator")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(addEducatorDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tournamentId").value(tournament.getTournamentId()));
    }
}
