package com.codearena.backend.judge.array;
import java.util.Arrays;
import java.util.Map;

public class PlusOneSolution implements ArraySolution {

    @Override
    public String inputKey() {
        return "digits";
    }

    @Override
    public Object execute(Map<String, Object> inputMap) {
        int[] digits =
                ArrayInputParser.getIntArray(
                        inputMap, "digits"
                );

        int n = digits.length;
        for (int i = n - 1; i >= 0; i--) {
            if (digits[i] < 9) {
                digits[i]++;
                return digits;
            }
            digits[i] = 0;
        }

        int[] res = new int[n + 1];
        res[0] = 1;
        return res;
    }

    @Override
    public boolean matches(
            Object actualOutput,
            String expectedOutputJson) {

        int[] expected =
                ArrayOutputParser.asIntArray(
                        ArrayOutputParser.parseRaw(
                                expectedOutputJson
                        )
                );

        return Arrays.equals(
                (int[]) actualOutput,
                expected
        );
    }
}
