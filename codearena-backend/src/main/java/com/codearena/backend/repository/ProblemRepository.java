// package com.codearena.backend.repository;

// import java.util.Optional;

// import org.springframework.data.jpa.repository.JpaRepository;
// import com.codearena.backend.entity.Problem;

// public interface ProblemRepository extends JpaRepository<Problem, Long> {

//     Optional<Problem> findByProblemSlug(String problemSlug);
// }







package com.codearena.backend.repository;

import java.util.List;
import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.codearena.backend.entity.Problem;

public interface ProblemRepository extends JpaRepository<Problem, Long> {

    Optional<Problem> findByProblemSlug(String problemSlug);

    // 🔥 ADD THIS METHOD
    @Query("SELECT p FROM Problem p WHERE LOWER(p.topics) LIKE LOWER(CONCAT('%', :topic, '%'))")
    List<Problem> findByTopic(@Param("topic") String topic);
}