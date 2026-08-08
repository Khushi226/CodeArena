package com.codearena.backend.entity;

import jakarta.persistence.*;

// Shares its primary key with Problem.id (1:1) — one row per problem,
// same pattern as Testcase being keyed by problemId but without needing
// its own auto-increment id since there's exactly one signature per problem.
@Entity
@Table(name = "problem_meta")
public class ProblemMetaEntity {

    @Id
    private Long problemId;

    @Column(nullable = false)
    private String methodName;

    @Column(nullable = false)
    private String returnType;

    // JSON array of {"name": "...", "type": "..."} — parsed/serialized via
    // ObjectMapper in ProblemMetaLoader and ProblemImportService.
    @Lob
    @Column(name = "params_json", columnDefinition = "TEXT", nullable = false)
    private String paramsJson;

    // Only set when returnType = "void" — see ProblemMeta for what this means.
    @Column(name = "output_param")
    private String outputParam;

    public String getOutputParam() {
        return outputParam;
    }

    public void setOutputParam(String outputParam) {
        this.outputParam = outputParam;
    }

    public Long getProblemId() {
        return problemId;
    }

    public void setProblemId(Long problemId) {
        this.problemId = problemId;
    }

    public String getMethodName() {
        return methodName;
    }

    public void setMethodName(String methodName) {
        this.methodName = methodName;
    }

    public String getReturnType() {
        return returnType;
    }

    public void setReturnType(String returnType) {
        this.returnType = returnType;
    }

    public String getParamsJson() {
        return paramsJson;
    }

    public void setParamsJson(String paramsJson) {
        this.paramsJson = paramsJson;
    }
}