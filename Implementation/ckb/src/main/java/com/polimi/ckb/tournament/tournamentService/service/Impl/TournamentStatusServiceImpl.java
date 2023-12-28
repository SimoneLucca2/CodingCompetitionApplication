package com.polimi.ckb.tournament.tournamentService.service.Impl;

import com.polimi.ckb.tournament.tournamentService.dto.ChangeTournamentStatusMessage;
import com.polimi.ckb.tournament.tournamentService.entity.Tournament;
import com.polimi.ckb.tournament.tournamentService.exception.TournamentNotFoundException;
import com.polimi.ckb.tournament.tournamentService.repository.TournamentRepository;
import com.polimi.ckb.tournament.tournamentService.service.TournamentStatusService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.Valid;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TournamentStatusServiceImpl implements TournamentStatusService {

    private final TournamentRepository tournamentRepository;

    @Transactional @Override
    public Tournament updateTournamentStatus(@Valid ChangeTournamentStatusMessage msg) {
        Optional<Tournament> maybeTournament = tournamentRepository.findById(msg.tournamentId());
        if(maybeTournament.isEmpty()) {
            throw new TournamentNotFoundException();
        }

        Tournament tournament = maybeTournament.get();
        tournament.setStatus(msg.status());
        return tournamentRepository.save(tournament);
    }
}
