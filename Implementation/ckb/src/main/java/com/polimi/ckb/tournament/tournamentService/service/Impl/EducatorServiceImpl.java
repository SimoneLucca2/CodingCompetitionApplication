package com.polimi.ckb.tournament.tournamentService.service.Impl;

import com.polimi.ckb.tournament.tournamentService.dto.AddEducatorMessage;
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
    public Educator addEducatorToTournament(AddEducatorMessage message) {
        Tournament tournament = getTournamentById(message.tournamentId());
        Educator educator = getEducatorById(message.educatorId());
        checkIfEducatorIsAlreadyPartOfTheTournament(tournament, educator);
        return addEducatorToTournamentAndSave(tournament, educator);
    }

    /**
     * Retrieves a Tournament by its ID.
     *
     * @param id The ID of the Tournament.
     * @return The Tournament with the specified ID.
     * @throws TournamentNotFoundException if the Tournament with the given ID is not found.
     */
    private Tournament getTournamentById(Long id) {
        Optional<Tournament> optionalTournament = tournamentRepository.findById(id);
        if(optionalTournament.isEmpty()) throw new TournamentNotFoundException();
        return optionalTournament.get();
    }

    /**
     * Retrieves an Educator by its id.
     *
     * @param id The id of the Educator.
     * @return The Educator with the given id.
     * @throws EducatorNotFoundException if the Educator with the given id is not found.
     */
    private Educator getEducatorById(Long id) {
        Optional<Educator> optionalEducator = educatorRepository.findById(id);
        if(optionalEducator.isEmpty()) throw new EducatorNotFoundException(id);
        return optionalEducator.get();
    }

    /**
     * Checks if an educator is already part of a tournament.
     *
     * @param tournament the tournament to check
     * @param educator the educator to check if already present
     * @throws EducatorAlreadyPresentException if the educator is already part of the tournament
     */
    private void checkIfEducatorIsAlreadyPartOfTheTournament(Tournament tournament, Educator educator) {
        if (tournament.getOrganizers().contains(educator)) throw new EducatorAlreadyPresentException();
    }

    /**
     * Adds an Educator to a Tournament and saves the changes to the tournament.
     *
     * @param tournament The Tournament object to add the Educator to.
     * @param educator   The Educator object to be added to the Tournament.
     * @return The Educator object that has been added to the Tournament.
     */
    private Educator addEducatorToTournamentAndSave(Tournament tournament, Educator educator) {
        tournament.getOrganizers().add(educator);
        tournamentRepository.save(tournament);
        return educator;
    }

    /**
     * Converts an AddEducatorMessage object to an Educator object.
     *
     * @param addEducatorMessage The AddEducatorMessage object to be converted.
     * @return The converted Educator object.
     */
    private Educator convertToEntity(AddEducatorMessage addEducatorMessage) {
        Educator educator = new Educator();
        educator.setId(addEducatorMessage.educatorId());
        return educator;
    }
}
