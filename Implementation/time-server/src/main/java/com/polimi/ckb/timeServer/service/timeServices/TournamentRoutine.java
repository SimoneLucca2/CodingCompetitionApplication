package com.polimi.ckb.timeServer.service.timeServices;

import com.polimi.ckb.timeServer.repository.TournamentRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Timer;

@Service
public class TournamentRoutine {

    private final TournamentRepository tournamentRepository;

    public TournamentRoutine(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    //todo polling to battle to check the expired deadlines
    public void checkTournamentDeadlines() {
        List<Long> tournaments = tournamentRepository.findAllWithDeadlineAfterAndStatusPreparation();
    }

}
