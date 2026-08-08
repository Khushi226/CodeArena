package com.codearena.backend.dto;

import lombok.Getter;
import lombok.Setter;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
public class ContestCreateRequest {

    private String title;
    private String description;

    // Frontend sends these as ISO-8601 UTC strings (e.g. "2026-08-10T14:00:00Z").
    // Jackson deserializes straight into Instant — no manual parsing needed.
    private Instant startTime;
    private Instant endTime;

    private List<ProblemEntry> problems;

    @Getter
    @Setter
    public static class ProblemEntry {
        private Long problemId;
        private Integer points;
        private Integer orderIndex;
    }
}