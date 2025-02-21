package com.polimi.ckb.tournament.service.Impl;

import com.polimi.ckb.tournament.controller.TournamentRankingController;
import com.polimi.ckb.tournament.dto.RankingEntryDto;
import com.polimi.ckb.tournament.entity.Score;
import com.polimi.ckb.tournament.entity.Tournament;
import com.polimi.ckb.tournament.exception.TournamentNotFoundException;
import com.polimi.ckb.tournament.repository.ScoreRepository;
import com.polimi.ckb.tournament.repository.TournamentRepository;
import com.polimi.ckb.tournament.service.RankingService;
import lombok.RequiredArgsConstructor;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
public class RankingServiceImpl implements RankingService {

    private final ScoreRepository scoreRepository;
    private final TournamentRepository tournamentRepository;
    private static final Logger log = LoggerFactory.getLogger(RankingServiceImpl.class);

    @Override
    public List<RankingEntryDto> getTournamentRanking(Long tournamentId, Integer firstIndex, Integer lastIndex) {

        if(firstIndex == null) firstIndex = 0;
        if(lastIndex == null) lastIndex = Integer.MAX_VALUE - 1;

        log.info("Retrieving ranking of tournament {} from {} to {}", tournamentId, firstIndex, lastIndex);

        Tournament tournament = tournamentRepository.findById(tournamentId)
                .orElseThrow(TournamentNotFoundException::new);

        List<Score> scores = scoreRepository.findByTournament(tournament);

        return scores.stream()
                .sorted((s1, s2) -> s2.getScoreValue().compareTo(s1.getScoreValue()))
                //to complete .skip(firstIndex - 1)
                //to complete .limit(lastIndex - firstIndex + 1)
                .map(score -> new RankingEntryDto(
                        score.getStudent().getStudentId(),
                        score.getScoreValue()))
                .collect(Collectors.toList());
    }
}
