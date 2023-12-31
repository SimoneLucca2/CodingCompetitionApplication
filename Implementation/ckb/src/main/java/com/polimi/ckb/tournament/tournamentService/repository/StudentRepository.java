package com.polimi.ckb.tournament.tournamentService.repository;

import com.polimi.ckb.tournament.tournamentService.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;

public interface StudentRepository extends JpaRepository<Student, Long> {

}