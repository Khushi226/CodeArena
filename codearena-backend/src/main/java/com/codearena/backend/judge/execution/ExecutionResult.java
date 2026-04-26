package com.codearena.backend.judge.execution;

public class ExecutionResult {

    private boolean success;
    private boolean compileError;
    private boolean runtimeError;

    private String output;

    public static ExecutionResult success(String output) {
        ExecutionResult r = new ExecutionResult();
        r.success = true;
        r.output = output;
        return r;
    }

    public static ExecutionResult compileError(String msg) {
        ExecutionResult r = new ExecutionResult();
        r.compileError = true;
        r.output = msg;
        return r;
    }

    public static ExecutionResult runtimeError(String msg) {
        ExecutionResult r = new ExecutionResult();
        r.runtimeError = true;
        r.output = msg;
        return r;
    }

    public boolean isSuccess() {
        return success;
    }

    public boolean isCompileError() {
        return compileError;
    }

    public boolean isRuntimeError() {
        return runtimeError;
    }

    public String getOutput() {
        return output;
    }
}