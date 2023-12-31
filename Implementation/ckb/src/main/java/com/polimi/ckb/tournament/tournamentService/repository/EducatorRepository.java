package com.polimi.ckb.tournament.tournamentService.repository;

import com.polimi.ckb.tournament.tournamentService.entity.Educator;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EducatorRepository extends JpaRepository<Educator, Long> {
}
