package com.polimi.ckb.tournament.dto;

import com.polimi.ckb.tournament.config.TournamentStatus;
import com.polimi.ckb.tournament.entity.Educator;
import com.polimi.ckb.tournament.entity.Student;
import com.polimi.ckb.tournament.entity.Tournament;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.List;
import java.util.stream.Collectors;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class TournamentDto {

    private Long tournamentId;
    private String name;
    private Long creatorId;
    private String registrationDeadline;
    private TournamentStatus status;
    private List<Long> organizerIds;
    private List<Long> participantsIds;

   public static TournamentDto fromEntity(Tournament tournament) {
        return TournamentDto.builder()
                .tournamentId(tournament.getTournamentId())
                .name(tournament.getName())
                .creatorId(tournament.getCreatorId())
                .registrationDeadline(tournament.getRegistrationDeadline())
                .status(tournament.getStatus())
                .participantsIds(tournament.getParticipants() != null ?
                                  tournament.getParticipants().stream().map(Student::getStudentId).collect(Collectors.toList())
                                  : null)
                .organizerIds(tournament.getOrganizers() != null ?
                              tournament.getOrganizers().stream().map(Educator::getEducatorId).collect(Collectors.toList())
                              : null)
                .build();
    }
}