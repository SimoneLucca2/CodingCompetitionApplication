package com.polimi.ckb.user.repository;

import com.polimi.ckb.user.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
