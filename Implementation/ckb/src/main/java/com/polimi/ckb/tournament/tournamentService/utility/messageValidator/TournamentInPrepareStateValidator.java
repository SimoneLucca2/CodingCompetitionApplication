package com.polimi.ckb.tournament.tournamentService.utility.messageValidator;

import com.polimi.ckb.tournament.tournamentService.config.TournamentStatus;
import com.polimi.ckb.tournament.tournamentService.entity.Tournament;
import com.polimi.ckb.tournament.tournamentService.repository.TournamentRepository;
import com.polimi.ckb.tournament.tournamentService.utility.messageValidator.annotation.TournamentInPrepareState;
import lombok.AllArgsConstructor;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

@AllArgsConstructor
public class TournamentInPrepareStateValidator implements ConstraintValidator<TournamentInPrepareState, Long> {

    private final TournamentRepository tournamentRepository;

    @Override
    public boolean isValid(Long tournamentId, ConstraintValidatorContext context) {
        Tournament tournament = tournamentRepository.findById(tournamentId).orElse(null);
        return tournament != null && tournament.getStatus().equals(TournamentStatus.PREPARATION);
    }
}
