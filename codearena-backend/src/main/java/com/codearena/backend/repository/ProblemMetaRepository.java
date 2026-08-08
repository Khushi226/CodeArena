package com.codearena.backend.repository;

import com.codearena.backend.entity.ProblemMetaEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProblemMetaRepository extends JpaRepository<ProblemMetaEntity, Long> {
}