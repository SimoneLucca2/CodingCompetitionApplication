package com.polimi.ckb.tournament.repository;

import com.polimi.ckb.tournament.entity.Educator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducatorRepository extends JpaRepository<Educator, Long> {
}