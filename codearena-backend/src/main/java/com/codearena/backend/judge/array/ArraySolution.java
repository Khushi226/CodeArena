package com.codearena.backend.judge.array;

import java.util.Map;

public interface ArraySolution {

    /**
     * Execute solution logic.
     * The solution itself extracts required inputs from inputMap.
     *
     * @param inputMap parsed input_json as Map
     * @return actual output (int[], Integer, Boolean, etc.)
     */
    Object execute(Map<String, Object> inputMap);

    /**
     * Compare actual output with expected output_json.
     * Solution decides how to parse and compare.
     *
     * @param actualOutput result from execute()
     * @param expectedOutputJson raw output_json from DB
     * @return true if output matches, false otherwise
     */
    boolean matches(Object actualOutput, String expectedOutputJson);

    String inputKey();
}
