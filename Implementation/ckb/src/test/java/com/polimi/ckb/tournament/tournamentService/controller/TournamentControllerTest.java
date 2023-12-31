package com.polimi.ckb.tournament.tournamentService.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.polimi.ckb.tournament.tournamentService.dto.CreateTournamentDto;
import com.polimi.ckb.tournament.tournamentService.entity.Tournament;
import com.polimi.ckb.tournament.tournamentService.service.TournamentService;
import com.polimi.ckb.tournament.tournamentService.service.kafkaProducer.TournamentCreationKafkaProducer;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import static com.polimi.ckb.tournament.tournamentService.config.TournamentStatus.PREPARATION;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.*;

class TournamentControllerTest {

    @Mock
    private TournamentService tournamentService;

    @Mock
    private TournamentCreationKafkaProducer kafkaProducer;

    @InjectMocks
    private TournamentController tournamentController;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
    }


    @Test
    void createTournament_Success() throws JsonProcessingException {
        CreateTournamentDto message = CreateTournamentDto
                .builder()
                .creatorId("creator1")
                .name("tournament1")
                .registrationDeadline("2021-01-01:00:00:00")
                .build();
        Tournament expectedTournament = Tournament
                .builder()
                .status(PREPARATION)
                .name("tournament1")
                .creatorId("creator1")
                .registrationDeadline("2021-01-01:00:00:00")
                .build();
        when(tournamentService.saveTournament(any(CreateTournamentDto.class))).thenReturn(expectedTournament);

        ResponseEntity<Object> response = tournamentController.createTournament(message);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertEquals(expectedTournament, response.getBody());
        verify(tournamentService, times(1)).saveTournament(message);
        verify(kafkaProducer, times(1)).sendTournamentCreationMessage(message);
    }

}
