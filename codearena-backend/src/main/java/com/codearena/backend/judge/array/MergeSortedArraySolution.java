package com.codearena.backend.judge.array;

import java.util.Arrays;
import java.util.Map;

public class MergeSortedArraySolution implements ArraySolution {

    @Override
    public Object execute(Map<String, Object> inputMap) {

        int[] nums1 =
                ArrayInputParser.getIntArray(inputMap, "nums1");
        int m =
                ArrayInputParser.getInt(inputMap, "m");

        int[] nums2 =
                ArrayInputParser.getIntArray(inputMap, "nums2");
        int n =
                ArrayInputParser.getInt(inputMap, "n");

        int i = m - 1;
        int j = n - 1;
        int k = m + n - 1;

        // Merge from end
        while (i >= 0 && j >= 0) {
            if (nums1[i] > nums2[j]) {
                nums1[k--] = nums1[i--];
            } else {
                nums1[k--] = nums2[j--];
            }
        }

        // Copy remaining nums2 if any
        while (j >= 0) {
            nums1[k--] = nums2[j--];
        }

        return nums1;
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
