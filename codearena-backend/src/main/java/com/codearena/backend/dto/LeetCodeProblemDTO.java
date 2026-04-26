// 






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
}