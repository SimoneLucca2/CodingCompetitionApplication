package com.polimi.ckb.user.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import com.polimi.ckb.user.entity.Battle;
import com.polimi.ckb.user.entity.Educator;
import com.polimi.ckb.user.entity.Tournament;
import com.polimi.ckb.user.service.TournamentService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import javax.validation.constraints.NotBlank;
import java.io.Serializable;

@Builder
@AllArgsConstructor
@NoArgsConstructor
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class CreateBattleDto implements Serializable {
    Long battleId;
    Long tournamentId;
    Long creatorId;
}