package com.polimi.ckb.tournament.tournamentService.utility.score;

import com.polimi.ckb.tournament.tournamentService.entity.Score;
import com.polimi.ckb.tournament.tournamentService.entity.ScoreId;
import com.polimi.ckb.tournament.tournamentService.entity.Student;
import com.polimi.ckb.tournament.tournamentService.entity.Tournament;
import com.polimi.ckb.tournament.tournamentService.repository.ScoreRepository;

public class InitScore {
    public static Score initScore(Student student, Tournament tournament, ScoreRepository scoreRepository) {
        ScoreId scoreId = new ScoreId(student.getStudentId(), tournament.getTournamentId());

        Score newScore = new Score();
        newScore.setId(scoreId);
        newScore.setStudent(student);
        newScore.setTournament(tournament);
        newScore.setScoreValue(0);

        return scoreRepository.save(newScore);
    }
}
