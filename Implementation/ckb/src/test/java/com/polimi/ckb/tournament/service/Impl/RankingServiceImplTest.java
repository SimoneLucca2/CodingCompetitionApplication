package com.polimi.ckb.tournament.service.Impl;

import com.polimi.ckb.tournament.dto.RankingEntryDto;
import com.polimi.ckb.tournament.entity.Score;
import com.polimi.ckb.tournament.entity.Student;
import com.polimi.ckb.tournament.entity.Tournament;
import com.polimi.ckb.tournament.exception.TournamentNotFoundException;
import com.polimi.ckb.tournament.repository.ScoreRepository;
import com.polimi.ckb.tournament.repository.TournamentRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
public class RankingServiceImplTest {

    @Mock
    private ScoreRepository scoreRepository;
    @Mock
    private TournamentRepository tournamentRepository;
    @InjectMocks
    private RankingServiceImpl rankingService;

    @BeforeEach
    public void setUp() {
        tournamentRepository = mock(TournamentRepository.class);
        tournamentRepository.save(Tournament.builder().tournamentId(1L).build());
    }

    @Test
    public void getTournamentRanking_Success() {
        Long tournamentId = 1L;
        Tournament tournament = new Tournament();
        when(tournamentRepository.findById(tournamentId)).thenReturn(Optional.of(tournament));

        Score score1 = createScore(100L, 90);
        Score score2 = createScore(101L, 100);

        when(scoreRepository.findByTournament(tournament)).thenReturn(Arrays.asList(score1, score2));

        List<RankingEntryDto> ranking = rankingService.getTournamentRanking(tournamentId, 1, 2);

        assertEquals(2, ranking.size());
        assertEquals(101L, ranking.get(0).getStudentId());
        assertEquals(100, ranking.get(0).getScoreValue());
    }

    @Test
    public void getTournamentRanking_TournamentNotFound() {
        Long tournamentId = 33L;

        assertThrows(TournamentNotFoundException.class, () -> rankingService.getTournamentRanking(tournamentId, 1, 1));
    }

    @Test
    public void getTournamentRanking_WithNullIndexes_ShouldReturnAllScoresSorted() {
        // Arrange
        Long tournamentId = 1L;
        Tournament tournament = Tournament.builder().tournamentId(tournamentId).build();

        // Mock scores in descending order
        Score score1 = createScore(1L, 90);
        Score score2 = createScore(2L, 80);
        Score score3 = createScore(3L, 70);
        List<Score> mockedScores = Arrays.asList(score1, score2, score3);
        when(scoreRepository.findByTournament(tournament)).thenReturn(mockedScores);

        // Act
        when(tournamentRepository.findById(tournamentId)).thenReturn(Optional.of(tournament));
        List<RankingEntryDto> ranking = rankingService.getTournamentRanking(tournamentId, null, null);

        // Assert
        assertEquals(3, ranking.size());
        assertEquals(Long.valueOf(1L), ranking.get(0).getStudentId());
        assertEquals(Integer.valueOf(90), ranking.get(0).getScoreValue());
    }

    @Test
    public void getTournamentRanking_InvalidIndexes() {
        Long tournamentId = 1L;
        Tournament tournament = new Tournament();
        when(tournamentRepository.findById(tournamentId)).thenReturn(Optional.of(tournament));

        Score score1 = createScore(100L, 90);
        Score score2 = createScore(101L, 100);
        when(scoreRepository.findByTournament(tournament)).thenReturn(Arrays.asList(score1, score2));

        // lastIndex less than firstIndex
        List<RankingEntryDto> ranking = rankingService.getTournamentRanking(tournamentId, 2, 1);

        assertTrue(ranking.isEmpty());
    }

    private Score createScore(Long studentId, int scoreValue) {
        Score score = new Score();
        score.setScoreValue(scoreValue);
        Student student = new Student();
        student.setStudentId(studentId);
        score.setStudent(student);
        return score;
    }
}
