package com.codearena.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

// Deliberately does NOT include the problem statement/starter code.
// That's only revealed via ContestController.getContestProblem(), which
// checks contest.startTime server-side before returning anything.
@Getter
@AllArgsConstructor
public class ContestProblemSummary {
    private Long problemId;
    private String title;
    private Integer points;
    private Integer orderIndex;
}