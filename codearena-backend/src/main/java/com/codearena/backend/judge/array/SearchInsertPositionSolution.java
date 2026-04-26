package com.codearena.backend.judge.array;

import java.util.Map;

public class SearchInsertPositionSolution implements ArraySolution {

    @Override
    public Object execute(Map<String, Object> inputMap) {

        // Extract inputs
        int[] nums =
                ArrayInputParser.getIntArray(inputMap, "nums");

        int target =
                ArrayInputParser.getInt(inputMap, "target");

        // Binary search
        int left = 0, right = nums.length - 1;

        while (left <= right) {
            int mid = left + (right - left) / 2;

            if (nums[mid] == target) {
                return mid;
            } else if (nums[mid] < target) {
                left = mid + 1;
            } else {
                right = mid - 1;
            }
        }

        return left;
    }

    @Override
    public boolean matches(Object actualOutput, String expectedOutputJson) {

        int expected =
                ArrayOutputParser.asInt(
                        ArrayOutputParser.parseRaw(expectedOutputJson)
                );

        return ((Integer) actualOutput) == expected;
    }

    @Override
    public String inputKey() {
        // TODO Auto-generated method stub
        throw new UnsupportedOperationException("Unimplemented method 'inputKey'");
    }
}
