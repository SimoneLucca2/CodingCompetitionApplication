package com.polimi.ckb.tournament.service.Impl;

import com.polimi.ckb.tournament.dto.NewUserDto;
import com.polimi.ckb.tournament.entity.Student;
import com.polimi.ckb.tournament.repository.StudentRepository;
import com.polimi.ckb.tournament.service.StudentService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@RequiredArgsConstructor
@Service
public class StudentServiceImpl implements StudentService {

    private final StudentRepository studentRepository;

    @Override
    @Transactional
    public void addNewUser(NewUserDto msg) {
        studentRepository.save(convertToEntity(msg));
    }

    private Student convertToEntity(NewUserDto msg) {
        return Student.builder().studentId(msg.getUserId()).build();
    }
}
