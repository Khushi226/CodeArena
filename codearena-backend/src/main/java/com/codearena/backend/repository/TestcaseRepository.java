package com.codearena.backend.repository;
import org.springframework.data.jpa.repository.JpaRepository;

import com.codearena.backend.entity.Testcase;

import java.util.List;

public interface TestcaseRepository extends JpaRepository<Testcase, Long> {

    List<Testcase> findByProblemId(Long problemId);
    List<Testcase> findByProblemIdAndVisibility(
        Long problemId,
        Testcase.Visibility visibility
    );
}


