package com.polimi.ckb.battle.battleService.mapper;

public interface Mapper<A, B> {

        B mapTo(A a);

        A mapFrom(B b);
}
