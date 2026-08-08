package com.codearena.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.LocalDateTime;

@Getter
@AllArgsConstructor
public class LeaderboardEntry {
    private int rank;
    private Long userId;
    private String username;
    private int totalScore;
    private LocalDateTime finishTime; // timestamp of their last accepted submission
}