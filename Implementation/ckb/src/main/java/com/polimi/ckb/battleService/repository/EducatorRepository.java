package com.polimi.ckb.battleService.repository;

import com.polimi.ckb.battleService.entity.Educator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducatorRepository extends JpaRepository<Educator, Long> {
}