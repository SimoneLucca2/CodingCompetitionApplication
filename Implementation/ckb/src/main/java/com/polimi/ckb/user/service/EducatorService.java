package com.polimi.ckb.user.service;

import com.polimi.ckb.user.dto.NewUserDto;
import com.polimi.ckb.user.entity.Educator;

import java.util.Optional;

public interface EducatorService {
    Optional<Educator> getEducatorById(Long id);
    void addNewEducator(NewUserDto msg);
}
