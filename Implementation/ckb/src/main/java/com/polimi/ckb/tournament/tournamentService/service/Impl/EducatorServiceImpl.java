package com.polimi.ckb.tournament.tournamentService.service.Impl;

import com.polimi.ckb.tournament.tournamentService.dto.AddEducatorDto;
import com.polimi.ckb.tournament.tournamentService.dto.NewUserDto;
import com.polimi.ckb.tournament.tournamentService.entity.Educator;
import com.polimi.ckb.tournament.tournamentService.entity.Tournament;
import com.polimi.ckb.tournament.tournamentService.exception.EducatorAlreadyPresentException;
import com.polimi.ckb.tournament.tournamentService.exception.EducatorNotFoundException;
import com.polimi.ckb.tournament.tournamentService.exception.TournamentNotFoundException;
import com.polimi.ckb.tournament.tournamentService.repository.EducatorRepository;
import com.polimi.ckb.tournament.tournamentService.repository.TournamentRepository;
import com.polimi.ckb.tournament.tournamentService.service.EducatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EducatorServiceImpl implements EducatorService {

    private final TournamentRepository tournamentRepository;
    private final EducatorRepository educatorRepository;

    /**
     * Adds an Educator to a Tournament.
     *
     * @param message The message containing the requesterId, tournamentId, and educatorId.
     * @return The added Educator.
     * @throws TournamentNotFoundException if the Tournament with the given tournamentId is not found.
     * @throws EducatorNotFoundException if the Educator with the given educatorId is not found.
     * @throws EducatorAlreadyPresentException if the Educator is already part of the Tournament.
     */
    @Override
    public Educator addEducatorToTournament(AddEducatorDto message) {
        Tournament tournament = getTournamentById(message.getTournamentId());

        //The educator must exist in the database
        Educator educator = getEducatorById(message.getEducatorId());

        // check if educator is already part of the tournament
        if (tournament.getOrganizers().contains(educator)) throw new EducatorAlreadyPresentException();

        // add educator to tournament and save
        tournament.getOrganizers().add(educator);
        tournamentRepository.save(tournament);
        return educator;
    }

    @Override
    public void addNewUser(NewUserDto msg) {
        educatorRepository.save(convertToEntity(msg));
    }

    private Tournament getTournamentById(Long id) {
        Optional<Tournament> optionalTournament = tournamentRepository.findById(id);
        if(optionalTournament.isEmpty()) throw new TournamentNotFoundException();
        return optionalTournament.get();
    }

    private Educator getEducatorById(Long id) {
        Optional<Educator> optionalEducator = educatorRepository.findById(id);
        if(optionalEducator.isEmpty()) throw new EducatorNotFoundException(id);
        return optionalEducator.get();
    }

    private Educator convertToEntity(NewUserDto msg) {
        return Educator.builder().educatorId(msg.getUserId()).build();
    }
}
