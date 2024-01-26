package com.polimi.ckb.tournament.service.Impl;

import com.polimi.ckb.tournament.dto.AddEducatorDto;
import com.polimi.ckb.tournament.dto.NewUserDto;
import com.polimi.ckb.tournament.entity.Educator;
import com.polimi.ckb.tournament.entity.Tournament;
import com.polimi.ckb.tournament.exception.EducatorAlreadyPresentException;
import com.polimi.ckb.tournament.exception.EducatorNotFoundException;
import com.polimi.ckb.tournament.exception.TournamentNotFoundException;
import com.polimi.ckb.tournament.repository.EducatorRepository;
import com.polimi.ckb.tournament.repository.TournamentRepository;
import com.polimi.ckb.tournament.utility.entityConverter.NewUserDtoToUser;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.ArrayList;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.assertThrows;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class EducatorServiceImplTest {

    @Mock
    private TournamentRepository tournamentRepository;

    @Mock
    private EducatorRepository educatorRepository;
    @InjectMocks
    private EducatorServiceImpl service;

    @Test
    public void addEducatorToTournament_TournamentNotFoundException() {
        AddEducatorDto message = new AddEducatorDto(1L, 1L, 2L);
        when(tournamentRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(TournamentNotFoundException.class, () -> service.addEducatorToTournament(message));
    }

    @Test
    public void addEducatorToTournament_EducatorNotFoundException() {
        AddEducatorDto message = new AddEducatorDto(1L, 1L, 2L);
        when(tournamentRepository.findById(anyLong())).thenReturn(Optional.of(new Tournament()));
        when(educatorRepository.findById(anyLong())).thenReturn(Optional.empty());

        assertThrows(EducatorNotFoundException.class, () -> service.addEducatorToTournament(message));
    }

    @Test
    public void addEducatorToTournament_EducatorAlreadyPresentException() {
        AddEducatorDto message = new AddEducatorDto(1L, 1L, 2L);
        Tournament tournament = new Tournament();
        Educator educator = new Educator();
        tournament.getOrganizers().add(educator);
        when(tournamentRepository.findById(anyLong())).thenReturn(Optional.of(tournament));
        when(educatorRepository.findById(anyLong())).thenReturn(Optional.of(educator));

        assertThrows(EducatorAlreadyPresentException.class, () -> service.addEducatorToTournament(message));
    }

    @Test
    public void addEducatorToTournament_HappyPath() {
        AddEducatorDto message = new AddEducatorDto(1L, 1L, 2L);
        Tournament tournament = new Tournament();
        tournament.setOrganizers(new ArrayList<>());
        Educator educator = new Educator();
        when(tournamentRepository.findById(anyLong())).thenReturn(Optional.of(tournament));
        when(educatorRepository.findById(anyLong())).thenReturn(Optional.of(educator));

        Tournament result = service.addEducatorToTournament(message);

        assertTrue(result.getOrganizers().contains(educator));
        verify(tournamentRepository).save(tournament);
    }


    @Test
    public void testAddNewUser() {
        NewUserDto newUserDto = new NewUserDto();
        newUserDto.setUserId(1L);
        Educator user = NewUserDtoToUser.convertToEntity(newUserDto);

        when(educatorRepository.save(any(Educator.class))).thenReturn(user);
        service.addNewUser(newUserDto);

        verify(educatorRepository).save(any(Educator.class));
    }

}