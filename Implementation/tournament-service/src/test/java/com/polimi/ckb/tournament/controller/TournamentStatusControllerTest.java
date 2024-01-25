package com.polimi.ckb.tournament.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.polimi.ckb.tournament.config.TournamentStatus;
import com.polimi.ckb.tournament.dto.ChangeTournamentStatusDto;
import com.polimi.ckb.tournament.entity.Tournament;
import com.polimi.ckb.tournament.service.TournamentStatusService;
import com.polimi.ckb.tournament.service.kafkaProducer.TournamentStatusKafkaProducer;
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
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.put;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TournamentStatusController.class)
public class TournamentStatusControllerTest {

    @MockBean
    private TournamentStatusService tournamentStatusService;

    @MockBean
    private TournamentStatusKafkaProducer kafkaProducer;

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Test
    public void testUpdateTournamentStatus() throws Exception {
        ChangeTournamentStatusDto statusDto = new ChangeTournamentStatusDto();
        statusDto.setTournamentId(1L);
        statusDto.setEducatorId(2L);
        statusDto.setStatus(TournamentStatus.PREPARATION);

        Tournament tournament = new Tournament();
        tournament.setTournamentId(1L);
        tournament.setCreatorId(2L);
        tournament.setStatus(TournamentStatus.ACTIVE);

        given(tournamentStatusService.updateTournamentStatus(any(ChangeTournamentStatusDto.class))).willReturn(tournament);

        mockMvc.perform(put("/tournament/status")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(statusDto)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.tournamentId").value(tournament.getTournamentId()));
    }

}
