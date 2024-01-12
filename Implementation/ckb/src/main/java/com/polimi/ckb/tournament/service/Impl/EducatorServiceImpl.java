package com.polimi.ckb.tournament.service.Impl;

import com.polimi.ckb.tournament.dto.AddEducatorDto;
import com.polimi.ckb.tournament.dto.NewUserDto;
import com.polimi.ckb.tournament.entity.Educator;
import com.polimi.ckb.tournament.entity.Tournament;
import com.polimi.ckb.tournament.exception.EducatorAlreadyPresentException;
import com.polimi.ckb.tournament.exception.EducatorNotFoundException;
import com.polimi.ckb.tournament.exception.TournamentNotFoundException;
import com.polimi.ckb.tournament.repository.EducatorRepository;
import com.polimi.ckb.tournament.repository.TournamentRepository;
import com.polimi.ckb.tournament.service.EducatorService;
import com.polimi.ckb.tournament.utility.entityConverter.NewUserDtoToUser;
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
    public Tournament addEducatorToTournament(AddEducatorDto message) {
        Tournament tournament = getTournamentById(message.getTournamentId());

        //The educator must exist in the database
        Educator educator = getEducatorById(message.getEducatorId());

        // check if educator is already part of the tournament
        if (tournament.getOrganizers().contains(educator)) throw new EducatorAlreadyPresentException();

        // add educator to tournament and save
        tournament.getOrganizers().add(educator);
        tournamentRepository.save(tournament);
        return tournament;
    }

    @Override
    public void addNewUser(NewUserDto msg) {
        educatorRepository.save(NewUserDtoToUser.convertToEntity(msg));
    }

    private Tournament getTournamentById(Long id) {
        return tournamentRepository.findById(id)
                .orElseThrow(TournamentNotFoundException::new);
    }

    private Educator getEducatorById(Long id) {
        Optional<Educator> optionalEducator = educatorRepository.findById(id);
        if(optionalEducator.isEmpty()) throw new EducatorNotFoundException(id);
        return optionalEducator.get();
    }

}
