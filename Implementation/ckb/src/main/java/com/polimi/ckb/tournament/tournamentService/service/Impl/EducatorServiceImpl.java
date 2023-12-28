package com.polimi.ckb.tournament.tournamentService.service.Impl;

import com.polimi.ckb.tournament.tournamentService.dto.AddEducatorMessage;
import com.polimi.ckb.tournament.tournamentService.entity.Educator;
import com.polimi.ckb.tournament.tournamentService.entity.Tournament;
import com.polimi.ckb.tournament.tournamentService.exception.EducatorAlreadyPresentException;
import com.polimi.ckb.tournament.tournamentService.exception.TournamentNotFoundException;
import com.polimi.ckb.tournament.tournamentService.repository.TournamentRepository;
import com.polimi.ckb.tournament.tournamentService.service.EducatorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class EducatorServiceImpl implements EducatorService {

    private final TournamentRepository tournamentRepository;

    @Autowired
    public EducatorServiceImpl(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    @Override
    public Educator addEducatorToTournament(AddEducatorMessage message) {
        Optional<Tournament> optionalTournament = tournamentRepository.findById(message.tournamentId());

        if (optionalTournament.isPresent()) {

            Tournament tournament = optionalTournament.get();
            Educator educator = convertToEntity(message);

            // Check if the educator is already a part of the tournament
            if (!tournament.getEducators().contains(educator)) {
                tournament.getEducators().add(educator);
                tournamentRepository.save(tournament);
                return educator; // Educator added successfully
            } else {
                throw new EducatorAlreadyPresentException();
            }
        } else {
            throw new TournamentNotFoundException();
        }
    }

    private Educator convertToEntity(AddEducatorMessage addEducatorMessage) {
        Educator educator = new Educator();
        educator.setId(addEducatorMessage.educatorId());
        return educator;
    }
}
