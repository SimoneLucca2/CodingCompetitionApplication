package com.polimi.ckb.user.service;


import com.polimi.ckb.user.dto.NewUserDto;
import com.polimi.ckb.user.entity.Student;

public interface StudentService {
    /**
     * adds a new student to the tournament database,
     * the student can also not be in a tournament yet
     */
    void addNewStudent(NewUserDto msg);
    Student getStudent(Long studentId);
}
