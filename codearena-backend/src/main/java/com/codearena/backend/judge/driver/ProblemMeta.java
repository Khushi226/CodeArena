package com.codearena.backend.judge.driver;

import java.util.List;

public class ProblemMeta {

    private final String methodName;
    private final String returnType;
    private final List<Param> params;

    public ProblemMeta(String methodName, String returnType, List<Param> params) {
        this.methodName = methodName;
        this.returnType = returnType;
        this.params = params;
    }

    public String getMethodName() { return methodName; }
    public String getReturnType() { return returnType; }
    public List<Param> getParams() { return params; }

    public static class Param {
        public final String name;
        public final String type;

        public Param(String name, String type) {
            this.name = name;
            this.type = type;
        }
    }
}