package com.polimi.ckb.tournament.dto;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class UsernameDto implements Serializable {
    String username;
}