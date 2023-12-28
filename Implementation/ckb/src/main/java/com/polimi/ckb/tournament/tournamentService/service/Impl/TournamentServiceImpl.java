package com.polimi.ckb.tournament.tournamentService.service.Impl;

import com.polimi.ckb.tournament.tournamentService.dto.CreateTournamentMessage;
import com.polimi.ckb.tournament.tournamentService.entity.Tournament;
import com.polimi.ckb.tournament.tournamentService.repository.TournamentRepository;
import com.polimi.ckb.tournament.tournamentService.service.TournamentService;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class TournamentServiceImpl implements TournamentService {

    private final TournamentRepository tournamentRepository;

    public TournamentServiceImpl(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    @Transactional
    public Tournament saveTournament(CreateTournamentMessage msg) {
        Tournament tournament = convertToEntity(msg);
        return tournamentRepository.save(tournament);
    }

    @Transactional(readOnly = true)
    public Tournament getTournament(Long id) {
        return tournamentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tournament not found"));
    }

    private Tournament convertToEntity(CreateTournamentMessage createTournamentMessage) {
        Tournament tournament = new Tournament();
        tournament.setName(createTournamentMessage.name());
        tournament.setCreator(createTournamentMessage.creator());
        tournament.setRegistrationDeadline(createTournamentMessage.registrationDeadline());
        tournament.setStatus("prepare");
        return tournament;
    }
}
