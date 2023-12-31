package com.polimi.ckb.tournament.tournamentService.service.Impl;

import com.polimi.ckb.tournament.tournamentService.dto.AddEducatorDto;
import com.polimi.ckb.tournament.tournamentService.entity.Educator;
import com.polimi.ckb.tournament.tournamentService.entity.Tournament;
import com.polimi.ckb.tournament.tournamentService.exception.EducatorAlreadyPresentException;
import com.polimi.ckb.tournament.tournamentService.exception.EducatorNotFoundException;
import com.polimi.ckb.tournament.tournamentService.exception.TournamentNotFoundException;
import com.polimi.ckb.tournament.tournamentService.repository.EducatorRepository;
import com.polimi.ckb.tournament.tournamentService.repository.TournamentRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EducatorServiceImplTest {

    @InjectMocks
    private EducatorServiceImpl educatorService;

    @Mock
    private TournamentRepository tournamentRepository;

    @Mock
    private EducatorRepository educatorRepository;
    
    @Test
    public void addEducatorToTournament_educatorDoesNotExist_throwsException() {
        AddEducatorDto message = new AddEducatorDto(100L, 100L, 1900L);
        
        when(educatorRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EducatorNotFoundException.class, () -> {
            educatorService.addEducatorToTournament(message);
        });
    }

    @Test
    public void addEducatorToTournament_tournamentDoesNotExist_throwsException() {
        AddEducatorDto message = new AddEducatorDto(1L, 10L, 1L);
        
        when(tournamentRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(TournamentNotFoundException.class, () -> {
            educatorService.addEducatorToTournament(message);
        });
    }

    @Test
    public void addEducatorToTournament_educatorAlreadyInTournament_throwsException() {
        AddEducatorDto message = new AddEducatorDto(1L, 1L, 1L);
        
        Educator educator = new Educator();
        Tournament tournament = mock(Tournament.class);

        when(tournamentRepository.findById(message.getTournamentId())).thenReturn(Optional.of(tournament));
        when(educatorRepository.findById(message.getEducatorId())).thenReturn(Optional.of(educator));
        
        when(tournament.getOrganizers()).thenReturn(List.of(educator));
        
        assertThrows(EducatorAlreadyPresentException.class, () -> {
            educatorService.addEducatorToTournament(message);
        });
    }

    @Test
    public void addEducatorToTournament_educatorNotInTournament_educatorAdded() {
        AddEducatorDto message = new AddEducatorDto(1L, 1L, 1L);
        
        Educator educator = new Educator();
        Tournament tournament = mock(Tournament.class);

        when(tournamentRepository.findById(message.getTournamentId())).thenReturn(Optional.of(tournament));
        when(educatorRepository.findById(message.getEducatorId())).thenReturn(Optional.of(educator));

        when(tournament.getOrganizers()).thenReturn(new ArrayList<>());

        Educator result = educatorService.addEducatorToTournament(message);

        assertEquals(educator, result);
        verify(tournamentRepository).save(any(Tournament.class));
    }
}