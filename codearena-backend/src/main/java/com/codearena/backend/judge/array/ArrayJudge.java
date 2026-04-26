package com.codearena.backend.judge.array;

import com.codearena.backend.entity.Testcase;
import com.codearena.backend.entity.Testcase.Visibility;
import com.codearena.backend.judge.JudgeResult;
import com.codearena.backend.service.TestcaseService;

import java.util.List;
import java.util.Map;

public class ArrayJudge {

    private final TestcaseService testcaseService;

    public ArrayJudge(TestcaseService testcaseService) {
        this.testcaseService = testcaseService;
    }

    /**
     * Generic ARRAY judge.
     * Judge is completely problem-agnostic.
     */
    public JudgeResult judge(List<Testcase> testCases, ArraySolution solution) {

        // List<Testcase> testCases =
        //         testcaseService.getTestCasesForProblem(problemId);

        try {
            for (Testcase testCase : testCases) {

                // 1️⃣ Parse input_json -> Map
                Map<String, Object> inputMap =
                        ArrayInputParser.parseToMap(
                                testCase.getInputJson()
                        );

                // 2️⃣ Execute solution
                Object actualOutput =
                        solution.execute(inputMap);

                // 3️⃣ Compare with expected output_json
                boolean passed =
                        solution.matches(
                                actualOutput,
                                testCase.getOutputJson()
                        );

                if (!passed) {
                    return JudgeResult.WRONG_ANSWER;
                }
            }

            return JudgeResult.ACCEPTED;

        } catch (Exception e) {
            e.printStackTrace();
            return JudgeResult.RUNTIME_ERROR;
        }
    }
}
