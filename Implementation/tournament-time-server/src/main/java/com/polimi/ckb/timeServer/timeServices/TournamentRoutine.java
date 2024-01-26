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
     * This method is responsible for checking the deadlines of tournaments and updating their status accordingly.
     * It is annotated with "@Async" and "@Scheduled" to enable asynchronous execution and scheduling at a fixed rate.
     * The polling rate is provided through the "tournament.polling.rate" property.
     * <p>
     * The method retrieves the current time using a SimpleDateFormat and formats it as a string.
     * It then calls a method in the tournamentRepository to find all tournaments with a deadline after the current time
     * and a status of "PREPARATION".
     * It iterates over the list of tournaments and sets their status to "ACTIVE" using the setStatus method.
     * The updated tournaments are then saved using the tournamentRepository.
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