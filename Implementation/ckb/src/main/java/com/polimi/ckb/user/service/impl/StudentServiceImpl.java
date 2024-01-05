package com.polimi.ckb.user.service.impl;

import com.polimi.ckb.user.dto.NewUserDto;
import com.polimi.ckb.user.entity.Student;
import com.polimi.ckb.user.repository.StudentRepository;
import com.polimi.ckb.user.repository.UserRepository;
import com.polimi.ckb.user.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class StudentServiceImpl implements StudentService {

    private final UserRepository userRepository;
    private final StudentRepository studentRepository;

    @Override
    public void addNewStudent(NewUserDto msg) {
        studentRepository.findById(msg.getUserId()).ifPresentOrElse(
                user -> {
                    throw new IllegalArgumentException("User already exists");
                },
                () -> {
                    Student user = new Student();
                    user.setId(msg.getUserId());
                    user.setEmail(msg.getEmail());
                    user.setName(msg.getName());

                    userRepository.save(user);
                }
        );
    }
}
