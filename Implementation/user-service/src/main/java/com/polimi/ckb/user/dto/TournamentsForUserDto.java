package com.polimi.ckb.user.dto;

import com.polimi.ckb.user.entity.Tournament;
import lombok.Builder;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

@Data
@Builder
public class TournamentsForUserDto implements Serializable {
    List<Tournament> tournaments;
}