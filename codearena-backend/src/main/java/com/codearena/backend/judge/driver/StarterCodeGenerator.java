package com.codearena.backend.judge.driver;

public class StarterCodeGenerator {

    public static String generate(Long problemId) {

        ProblemMeta meta = ProblemMetaRegistry.get(problemId);
        if (meta == null) {
            throw new RuntimeException("Problem meta not found");
        }

        StringBuilder sb = new StringBuilder();

        sb.append("class Solution {\n");

        sb.append("    public ")
                .append(meta.getReturnType())
                .append(" ")
                .append(meta.getMethodName())
                .append("(");

        for (int i = 0; i < meta.getParams().size(); i++) {
            ProblemMeta.Param p = meta.getParams().get(i);
            sb.append(p.type).append(" ").append(p.name);

            if (i != meta.getParams().size() - 1) {
                sb.append(", ");
            }
        }

        sb.append(") {\n");
        sb.append("        \n");
        sb.append("    }\n");
        sb.append("}\n");

        return sb.toString();
    }
}