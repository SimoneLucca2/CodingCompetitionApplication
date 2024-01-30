package com.polimi.ckb.tournament.controller;

import com.polimi.ckb.tournament.dto.RankingEntryDto;
import com.polimi.ckb.tournament.service.RankingService;
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

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest(TournamentRankingController.class)
public class TournamentRankingControllerTest {

    @MockBean
    private RankingService rankingService;

    @Autowired
    private MockMvc mockMvc;

    @Test
    public void testGetTournamentRanking() throws Exception {
        long tournamentId = 1L;
        int minIndex = 0;
        int maxIndex = 10;
        List<RankingEntryDto> mockRanking = Arrays.asList(new RankingEntryDto(), new RankingEntryDto());

        given(rankingService.getTournamentRanking(tournamentId, minIndex, maxIndex)).willReturn(mockRanking);

        mockMvc.perform(get("/tournament/ranking/tournamentRanking")
                        .param("tournamentId", String.valueOf(tournamentId))
                        .param("minIndex", String.valueOf(minIndex))
                        .param("maxIndex", String.valueOf(maxIndex)))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()").value(mockRanking.size()));
    }
}
