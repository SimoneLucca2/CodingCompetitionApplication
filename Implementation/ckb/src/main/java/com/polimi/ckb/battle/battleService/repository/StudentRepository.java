package com.polimi.ckb.battle.battleService.repository;

import com.polimi.ckb.battle.battleService.entity.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface StudentRepository extends JpaRepository<Student, Long> {
}
