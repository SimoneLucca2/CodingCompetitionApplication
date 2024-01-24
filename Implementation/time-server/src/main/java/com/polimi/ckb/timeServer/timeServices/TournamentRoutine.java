package com.polimi.ckb.timeServer.timeServices;

import com.polimi.ckb.timeServer.config.TournamentStatus;
import com.polimi.ckb.timeServer.entity.Tournament;
import com.polimi.ckb.timeServer.repository.TournamentRepository;
import org.springframework.scheduling.annotation.Async;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Service;

import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;

@Service
public class TournamentRoutine {

    private final SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd'T'HH:mm:ss'Z'");
    private final TournamentRepository tournamentRepository;

    public TournamentRoutine(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    /**
     * Polling to tournament database to check the expired deadlines
     */
    @Async
    @Scheduled(fixedRateString = "#{T(java.lang.Integer).valueOf('${tournament.polling.rate}')}")
    public void checkTournamentDeadlines() {
        String currentTime = sdf.format(new Date());
        List<Tournament> tournaments = tournamentRepository.findAllWithDeadlineAfterAndStatusPreparation(currentTime);
        tournaments.forEach(tournament -> {
            tournament.setStatus(TournamentStatus.ACTIVE);
            tournamentRepository.save(tournament);
        });
    }

}