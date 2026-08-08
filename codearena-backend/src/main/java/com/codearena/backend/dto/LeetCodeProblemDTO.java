

package com.codearena.backend.dto;

import lombok.Data;
import java.util.List;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class LeetCodeProblemDTO {
    private String title;
    private String problem_slug;
    private String difficulty;
    private String description;
    private List<String> topics;
    private List<String> constraints;
    private List<ExampleDTO> examples; // Matches "examples" in JSON

    // Optional. Omit or leave null in the JSON for a normal public problem.
    // Set explicitly to false to import a contest-exclusive problem that
    // stays hidden from /problems until its contest ends.
    private Boolean visible;

    // Optional, but required in practice for anything beyond the statement —
    // without it, StarterCodeGenerator/JavaDriverGenerator can't produce
    // starter code or a judge driver for this problem. See ProblemImportService.
    private MethodSignatureDTO methodSignature;

    // Optional. Previously the only way to add test cases was a hand-written
    // SQL INSERT — now they can travel with the rest of the problem in one file.
    private List<TestcaseDTO> testcases;
}