package com.codearena.backend.repository;

import com.codearena.backend.entity.Contest;
import org.springframework.data.jpa.repository.JpaRepository;

import java.time.Instant;
import java.util.Optional;
import java.util.*;

public interface ContestRepository extends JpaRepository<Contest, Long> {

    Optional<Contest> findBySlug(String slug);
    List<Contest> findByEndTimeBefore(Instant time);
}