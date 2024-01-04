package com.polimi.ckb.user.service.impl;

import com.polimi.ckb.user.dto.NewUserDto;
import com.polimi.ckb.user.entity.Student;
import com.polimi.ckb.user.repository.TournamentRepository;
import com.polimi.ckb.user.repository.UserRepository;
import com.polimi.ckb.user.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl extends UserServiceImpl implements StudentService {

    private final UserRepository userRepository;
    private final TournamentRepository tournamentRepository;

    public StudentServiceImpl(UserRepository userRepository, TournamentRepository tournamentRepository) {
        super(userRepository);
        this.userRepository = userRepository;
        this.tournamentRepository = tournamentRepository;
    }

    @Override
    public void addNewUser(NewUserDto msg) {
        Student user = Student.builder()
                .studentid(msg.getUserId())
                .name(msg.getName())
                .email(msg.getEmail())
                .build();

        userRepository.save(user);
    }
}
