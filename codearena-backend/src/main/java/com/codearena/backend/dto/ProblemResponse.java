package com.codearena.backend.dto;

public class ProblemResponse {

    private Long id;
    private String title;
    private String description;
    private String difficulty;
    private String examples;
    private String constraints;
    private String starterCode;

    public ProblemResponse(Long id,
                           String title,
                           String description,
                           String difficulty,
                           String examples,
                           String constraints,
                           String starterCode) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.difficulty = difficulty;
        this.examples = examples;
        this.constraints = constraints;
        this.starterCode = starterCode;
    }

    // 🔹 Getters (important for JSON serialization)

    public Long getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getDifficulty() {
        return difficulty;
    }

    public String getExamples() {
        return examples;
    }

    public String getConstraints() {
        return constraints;
    }

    public String getStarterCode() {
        return starterCode;
    }
}