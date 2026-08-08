package com.codearena.backend.repository;

import com.codearena.backend.entity.ContestParticipant;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface ContestParticipantRepository extends JpaRepository<ContestParticipant, Long> {

    Optional<ContestParticipant> findByContestIdAndUserId(Long contestId, Long userId);

    boolean existsByContestIdAndUserId(Long contestId, Long userId);
}