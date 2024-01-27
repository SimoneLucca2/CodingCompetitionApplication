package com.polimi.ckb.timeServer.dto;

import com.polimi.ckb.timeServer.config.BattleStatus;
import lombok.*;

import java.io.Serializable;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class ChangeBattleStatusDto implements Serializable {
        private Long battleId;
        private BattleStatus status;
}
