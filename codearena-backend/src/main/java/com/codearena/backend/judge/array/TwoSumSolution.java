package com.codearena.backend.judge.array;

import java.util.Arrays;
import java.util.HashMap;
import java.util.Map;

public class TwoSumSolution implements ArraySolution {

    @Override
    public Object execute(Map<String, Object> inputMap) {

        int[] nums =
                ArrayInputParser.getIntArray(inputMap, "nums");

        int target =
                ArrayInputParser.getInt(inputMap, "target");

        Map<Integer, Integer> map = new HashMap<>();

        for (int i = 0; i < nums.length; i++) {
            int complement = target - nums[i];

            if (map.containsKey(complement)) {
                int a = map.get(complement);
                int b = i;

                // return indices in ascending order (for consistency)
                return new int[] {
                        Math.min(a, b),
                        Math.max(a, b)
                };
            }

            map.put(nums[i], i);
        }

        // According to problem, solution always exists
        throw new RuntimeException("No valid two sum solution found");
    }

    @Override
    public boolean matches(Object actualOutput, String expectedOutputJson) {

        int[] expected =
                ArrayOutputParser.asIntArray(
                        ArrayOutputParser.parseRaw(expectedOutputJson)
                );

        return Arrays.equals(
                (int[]) actualOutput,
                expected
        );
    }

    @Override
    public String inputKey() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'inputKey'");
    }
}
