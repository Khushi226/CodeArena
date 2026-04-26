package com.codearena.backend.entity;

import jakarta.persistence.*;

@Entity
@Table(name = "testcases")
public class Testcase {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "problem_id", nullable = false)
    private Long problemId;

    @Column(name = "input_json", columnDefinition = "json", nullable = false)
    private String inputJson;

    @Column(name = "output_json", columnDefinition = "json", nullable = false)
    private String outputJson;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private Visibility visibility;

    public enum Visibility {
        PUBLIC,
        PRIVATE
    }

    // getters & setters
    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getProblemId() {
        return problemId;
    }

    public void setProblemId(Long problemId) {
        this.problemId = problemId;
    }

    public String getInputJson() {
        return inputJson;
    }

    public void setInputJson(String inputJson) {
        this.inputJson = inputJson;
    }

    public String getOutputJson() {
        return outputJson;
    }

    public void setOutputJson(String outputJson) {
        this.outputJson = outputJson;
    }

    public Visibility getVisibility() {
        return visibility;
    }

    public void setVisibility(Visibility visibility) {
        this.visibility = visibility;
    }
}
