package com.codearena.backend.judge.array;
import java.util.Map;

public class MaximumSubarraySolution implements ArraySolution {

    @Override
    public String inputKey() {
        return "nums";
    }

    @Override
    public Object execute(Map<String, Object> inputMap) {
        int[] nums =
                ArrayInputParser.getIntArray(
                        inputMap, "nums"
                );

        int maxSoFar = nums[0];
        int curr = nums[0];

        for (int i = 1; i < nums.length; i++) {
            curr = Math.max(nums[i], curr + nums[i]);
            maxSoFar = Math.max(maxSoFar, curr);
        }
        return maxSoFar;
    }

    @Override
    public boolean matches(
            Object actualOutput,
            String expectedOutputJson) {

        int expected =
                ArrayOutputParser.asInt(
                        ArrayOutputParser.parseRaw(
                                expectedOutputJson
                        )
                );

        return ((Integer) actualOutput) == expected;
    }
}
