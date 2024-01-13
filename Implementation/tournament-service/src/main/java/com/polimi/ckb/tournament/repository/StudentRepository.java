package com.polimi.ckb.tournament.repository;

import com.polimi.ckb.tournament.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

}