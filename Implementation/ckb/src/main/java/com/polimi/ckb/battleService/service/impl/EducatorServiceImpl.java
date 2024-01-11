package com.polimi.ckb.battleService.service.impl;

import com.polimi.ckb.battleService.dto.NewUserDto;
import com.polimi.ckb.battleService.entity.Educator;
import com.polimi.ckb.battleService.repository.EducatorRepository;
import com.polimi.ckb.battleService.service.EducatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EducatorServiceImpl implements EducatorService {

    private final EducatorRepository educatorRepository;
    @Override
    public void addNewUser(NewUserDto newUserDto) {
        educatorRepository.save(convertToEntity(newUserDto));
    }

    private Educator convertToEntity(NewUserDto newUserDto) {
        return Educator.builder()
                .educatorId(newUserDto.getUserId()).build();
    }
}
