package com.polimi.ckb.tournament.tournamentService.service.Impl;

import com.polimi.ckb.tournament.tournamentService.dto.RankingEntryDto;
import com.polimi.ckb.tournament.tournamentService.entity.Score;
import com.polimi.ckb.tournament.tournamentService.entity.Tournament;
import com.polimi.ckb.tournament.tournamentService.repository.ScoreRepository;
import com.polimi.ckb.tournament.tournamentService.repository.TournamentRepository;
import com.polimi.ckb.tournament.tournamentService.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RankingServiceImpl implements RankingService {

    private final ScoreRepository scoreRepository;
    private final TournamentRepository tournamentRepository;

    @Override
    public List<RankingEntryDto> getTournamentRanking(Long tournamentId, Integer firstIndex, Integer lastIndex) {

        if(firstIndex == null) firstIndex = 0;
        if(lastIndex == null) lastIndex = Integer.MAX_VALUE;

        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(() -> new RuntimeException("Tournament not found"));

        List<Score> scores = scoreRepository.findByTournament(tournament);

        return scores.stream()
                .sorted((s1, s2) -> s2.getScoreValue().compareTo(s1.getScoreValue()))
                .skip(firstIndex - 1)
                .limit(lastIndex - firstIndex + 1)
                .map(score -> new RankingEntryDto(
                        score.getStudent().getStudentId(),
                        score.getScoreValue()))
                .collect(Collectors.toList());
    }
}
