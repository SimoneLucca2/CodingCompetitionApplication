package com.polimi.ckb.tournament.utility.messageValidator;

import com.polimi.ckb.tournament.repository.TournamentRepository;
import com.polimi.ckb.tournament.utility.messageValidator.annotation.TournamentExists;
import lombok.RequiredArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@RequiredArgsConstructor
public class TournamentExistsValidator implements ConstraintValidator<TournamentExists, Long> {

    private final TournamentRepository tournamentRepository;

    @Override
    public boolean isValid(Long tournamentId, ConstraintValidatorContext context) {
        return tournamentRepository.existsById(tournamentId);
    }
}
