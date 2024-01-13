package com.polimi.ckb.tournament.utility.messageValidator;

import com.polimi.ckb.tournament.entity.Tournament;
import com.polimi.ckb.tournament.repository.TournamentRepository;
import com.polimi.ckb.tournament.utility.messageValidator.annotation.IsCreator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;
import java.util.Optional;

public class IsCreatorValidator implements ConstraintValidator<IsCreator, Long> {

    private final TournamentRepository tournamentRepository;

    public IsCreatorValidator(TournamentRepository tournamentRepository) {
        this.tournamentRepository = tournamentRepository;
    }

    /**
     * Checks if the given tournament ID is the same as the creator ID of the tournament.
     *
     * @param tournamentId the ID of the tournament to check
     * @param context      the validation context
     * @return true if the given tournament ID is the same as the creator ID of the tournament, false otherwise
     */
    @Override
    public boolean isValid(Long tournamentId, ConstraintValidatorContext context) {
        return Objects.equals(getTournamentCreator(tournamentId), tournamentId);
    }

    /**
     * Retrieves the ID of the creator of a tournament.
     *
     * @param tournamentId the ID of the tournament
     * @return the ID of the tournament creator, or null if the tournament does not exist
     */
    private Long getTournamentCreator(Long tournamentId) {
        Optional<Tournament> maybeTournament = tournamentRepository.findById(tournamentId);
        return maybeTournament.map(Tournament::getCreatorId).orElse(null);
    }
}
