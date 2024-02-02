package com.polimi.ckb.user.service.impl;

import com.polimi.ckb.user.dto.NewUserDto;
import com.polimi.ckb.user.entity.User;
import com.polimi.ckb.user.repository.UserRepository;
import com.polimi.ckb.user.service.UserService;
import jakarta.persistence.EntityNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;

    @Transactional(readOnly = true) @Override
    public User getUser(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new EntityNotFoundException("user not found"));
    }

    @Override
    public void addNewUser(NewUserDto msg) {
        User user = User.builder()
                .userId(msg.getUserId())
                .name(msg.getName())
                .surname(msg.getSurname())
                .email(msg.getEmail())
                .nickname(msg.getNickname())
                .type(msg.getType())
                .build();
        userRepository.save(user);
    }

}
