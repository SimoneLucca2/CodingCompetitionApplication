package com.polimi.ckb.tournament.service.Impl;

import com.polimi.ckb.tournament.config.TournamentStatus;
import com.polimi.ckb.tournament.dto.ChangeTournamentStatusDto;
import com.polimi.ckb.tournament.entity.Tournament;
import com.polimi.ckb.tournament.exception.TournamentNotFoundException;
import com.polimi.ckb.tournament.repository.TournamentRepository;
import com.polimi.ckb.tournament.service.TournamentStatusService;
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
    public Tournament updateTournamentStatus(@Valid ChangeTournamentStatusDto msg) {

        Long tournamentId = msg.getTournamentId();
        TournamentStatus status = msg.getStatus();

        return updateTournamentStatus(tournamentId, status);
    }

    @Transactional @Override
    public Tournament updateTournamentStatus(Long tournamentId, TournamentStatus status) {
        Optional<Tournament> maybeTournament = tournamentRepository.findById(tournamentId);
        if(maybeTournament.isEmpty()) {
            throw new TournamentNotFoundException();
        }

        Tournament tournament = maybeTournament.get();
        tournament.setStatus(status);
        return tournamentRepository.save(tournament);
    }
}
