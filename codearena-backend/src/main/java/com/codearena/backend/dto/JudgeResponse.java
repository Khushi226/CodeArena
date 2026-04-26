package com.codearena.backend.dto;

import com.codearena.backend.judge.JudgeResult;

public class JudgeResponse {

    private JudgeResult result;
    private String message;

    private String input;
    private String expectedOutput;
    private String actualOutput;
    private Integer failedTestcaseIndex;


    public JudgeResponse() {}

    public JudgeResponse(JudgeResult result) {
        this.result = result;
    }

    public void setFailedTestcaseIndex(Integer index) {
        this.failedTestcaseIndex = index;
    }
    public Integer getFailedTestcaseIndex() {
        return failedTestcaseIndex;
    }

    public JudgeResult getResult() {
        return result;
    }

    public void setResult(JudgeResult result) {
        this.result = result;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public String getInput() {
        return input;
    }

    public void setInput(String input) {
        this.input = input;
    }

    public String getExpectedOutput() {
        return expectedOutput;
    }

    public void setExpectedOutput(String expectedOutput) {
        this.expectedOutput = expectedOutput;
    }

    public String getActualOutput() {
        return actualOutput;
    }

    public void setActualOutput(String actualOutput) {
        this.actualOutput = actualOutput;
    }
}
