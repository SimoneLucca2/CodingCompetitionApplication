package com.polimi.ckb.tournament.tournamentService.service.Impl;

import com.polimi.ckb.tournament.tournamentService.config.TournamentStatus;
import com.polimi.ckb.tournament.tournamentService.dto.CreateTournamentDto;
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
    public Tournament saveTournament(CreateTournamentDto msg) {
        Optional<Tournament> maybeTournament = tournamentRepository.findByName(msg.getName());
        if(maybeTournament.isPresent()) {
            throw new TournamentAlreadyExistException();
        }
        return tournamentRepository.save(convertToEntity(msg));
    }

    @Transactional(readOnly = true)
    public Tournament getTournament(Long id) {
        return tournamentRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("Tournament not found"));
    }

    private Tournament convertToEntity(CreateTournamentDto createTournamentDto) {
        return Tournament.builder()
                .name(createTournamentDto.getName())
                .creatorId(createTournamentDto.getCreatorId())
                .registrationDeadline(createTournamentDto.getRegistrationDeadline())
                .status(TournamentStatus.PREPARATION)
                .build();
    }
}
