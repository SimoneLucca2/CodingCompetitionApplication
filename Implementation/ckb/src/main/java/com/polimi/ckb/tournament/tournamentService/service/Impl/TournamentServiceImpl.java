package com.polimi.ckb.tournament.tournamentService.service.Impl;

import com.polimi.ckb.tournament.tournamentService.config.TournamentStatus;
import com.polimi.ckb.tournament.tournamentService.dto.CreateTournamentMessage;
import com.polimi.ckb.tournament.tournamentService.entity.Tournament;
import com.polimi.ckb.tournament.tournamentService.exception.TournamentAlreadyExistException;
import com.polimi.ckb.tournament.tournamentService.repository.TournamentRepository;
import com.polimi.ckb.tournament.tournamentService.service.TournamentService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class TournamentServiceImpl implements TournamentService {

    private final TournamentRepository tournamentRepository;

    @Transactional
    public Tournament saveTournament(CreateTournamentMessage msg) {
        Optional<Tournament> maybeTournament = tournamentRepository.findByName(msg.name());
        if(maybeTournament.isPresent()) {
            throw new TournamentAlreadyExistException();
        }
        return tournamentRepository.save(convertToEntity(msg));
    }

    @Transactional(readOnly = true)
    public Tournament getTournament(Long id) {
        return tournamentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tournament not found"));
    }

    private Tournament convertToEntity(CreateTournamentMessage createTournamentMessage) {
        return Tournament.builder()
                .name(createTournamentMessage.name())
                .creator_id(createTournamentMessage.creator())
                .registrationDeadline(createTournamentMessage.registrationDeadline())
                .status(TournamentStatus.PREPARATION)
                .build();
    }
}
