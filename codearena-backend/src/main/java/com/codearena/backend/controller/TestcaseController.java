package com.codearena.backend.controller;
import org.springframework.web.bind.annotation.*;

import com.codearena.backend.entity.Testcase;
import com.codearena.backend.judge.JudgeResult;
import com.codearena.backend.judge.array.ArrayInputParser;
import com.codearena.backend.judge.array.ArrayJudge;
import com.codearena.backend.judge.array.ArrayOutputParser;
import com.codearena.backend.judge.array.ArraySolution;
import com.codearena.backend.judge.array.ArraySolutionRegistry;
import com.codearena.backend.judge.array.PlusOneSolution;
import com.codearena.backend.service.TestcaseService;

import java.util.*;

@RestController
@RequestMapping("/debug")
public class TestcaseController {

    private final TestcaseService testCaseService;

    public TestcaseController(TestcaseService testCaseService) {
        this.testCaseService = testCaseService;
    }

    // @GetMapping("/testcases/{problemId}")
    // public List<Testcase> getTestCases(@PathVariable Long problemId) {
    //     return testCaseService.getTestCasesForProblem(problemId);
    // }

    // @GetMapping("/parse-input/{problemId}")
    // public String debugParseInput(@PathVariable Long problemId) {

    //     // 1️⃣ Fetch testcases
    //     List<Testcase> testCases =
    //             testCaseService.getTestCasesForProblem(problemId);

    //     // 2️⃣ Take ONLY the first testcase
    //     Testcase testCase = testCases.get(0);

    //     // 3️⃣ Parse input_json
    //     int[] digits = ArrayInputParser.parseDigits(testCase.getInputJson());

    //     // 4️⃣ Print to console
    //     System.out.println(Arrays.toString(digits));

    //     return "Parsed input printed in console. Check logs.";
    // }

    // @GetMapping("/parse-output/{problemId}")
    // public String debugParseOutput(@PathVariable Long problemId) {

    //     // 1️⃣ Fetch testcases
    //     List<Testcase> testCases =
    //             testCaseService.getTestCasesForProblem(problemId);

    //     // 2️⃣ Take ONLY the first testcase
    //     Testcase testCase = testCases.get(0);

    //     // 3️⃣ Parse output_json
    //     int[] expected =
    //             ArrayOutputParser.parseExpectedOutput(testCase.getOutputJson());

    //     // 4️⃣ Print to console
    //     System.out.println(Arrays.toString(expected));

    //     return "Parsed output printed in console. Check logs.";
    // }

    @GetMapping("/judge/array/{problemSlug}/{problemId}")
    public String judgeArrayProblem(
            @PathVariable String problemSlug,
            @PathVariable Long problemId) {

        ArraySolution solution =
                ArraySolutionRegistry.getSolution(problemSlug);

        if (solution == null) {
            return "NO_SOLUTION_FOUND";
        }

        ArrayJudge judge = new ArrayJudge(testCaseService);

        JudgeResult result =
                judge.judge(testCaseService.getTestCasesForProblem(problemId), solution);

        return result.name();
    }

}
