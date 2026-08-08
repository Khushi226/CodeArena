


package com.codearena.backend.judge.driver;

import java.util.List;

public class ProblemMeta {

    private final String methodName;
    private final String returnType;
    private final List<Param> params;

    // Only meaningful when returnType is "void": names the parameter whose
    // final value (after the call mutates it) should be printed and compared
    // against output_json. LeetCode-style in-place problems (e.g. "merge")
    // need this since there's no return value to print otherwise.
    private final String outputParam;

    public ProblemMeta(String methodName, String returnType, List<Param> params) {
        this(methodName, returnType, params, null);
    }

    public ProblemMeta(String methodName, String returnType, List<Param> params, String outputParam) {
        this.methodName = methodName;
        this.returnType = returnType;
        this.params = params;
        this.outputParam = outputParam;
    }

    public String getMethodName() { return methodName; }
    public String getReturnType() { return returnType; }
    public List<Param> getParams() { return params; }
    public String getOutputParam() { return outputParam; }

    public static class Param {
        public final String name;
        public final String type;

        public Param(String name, String type) {
            this.name = name;
            this.type = type;
        }
    }
}