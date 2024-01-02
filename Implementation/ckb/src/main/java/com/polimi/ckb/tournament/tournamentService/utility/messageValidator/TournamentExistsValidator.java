package com.polimi.ckb.tournament.tournamentService.utility.messageValidator;

import com.polimi.ckb.tournament.tournamentService.repository.TournamentRepository;
import com.polimi.ckb.tournament.tournamentService.utility.messageValidator.annotation.TournamentExists;
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
