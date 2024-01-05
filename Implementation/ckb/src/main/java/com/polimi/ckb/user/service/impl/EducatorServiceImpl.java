package com.polimi.ckb.user.service.impl;


import com.polimi.ckb.user.dto.NewUserDto;
import com.polimi.ckb.user.entity.Educator;
import com.polimi.ckb.user.repository.EducatorRepository;
import com.polimi.ckb.user.service.EducatorService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@RequiredArgsConstructor
public class EducatorServiceImpl implements EducatorService {

    private final EducatorRepository educatorRepository;

    @Override
    public Optional<Educator> getEducatorById(Long id) {
        return educatorRepository.findById(id);
    }


    @Override
    public void addNewEducator(NewUserDto msg) {
        educatorRepository.findById(msg.getUserId()).ifPresentOrElse(
                user -> {
                    throw new IllegalArgumentException("Educator already exists");
                },
                () -> {
                    Educator educator = new Educator();
                    educator.setId(msg.getUserId());
                    educator.setEmail(msg.getEmail());
                    educator.setName(msg.getName());

                    educatorRepository.save(educator);
                }
        );
    }
}
