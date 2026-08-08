package com.codearena.backend.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;

import java.time.Instant;
import java.util.List;

@Getter
@AllArgsConstructor
public class ContestResponse {
    private Long id;
    private String slug;
    private String title;
    private String description;
    private Instant startTime;
    private Instant endTime;

    // Computed server-side on every request — never stored. See ContestService.getStatus().
    private String status; // "UPCOMING" | "RUNNING" | "ENDED"

    private boolean registered; // is the requesting user already registered

    private List<ContestProblemSummary> problems; // titles/points only, see ContestProblemSummary
}