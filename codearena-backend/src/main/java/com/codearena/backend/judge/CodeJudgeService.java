// // package com.codearena.backend.judge;

// // import com.codearena.backend.dto.JudgeResponse;
// // import com.codearena.backend.entity.Testcase;
// // import com.codearena.backend.judge.driver.JavaDriverGenerator;
// // import com.codearena.backend.judge.execution.CodeExecutionService;
// // import com.codearena.backend.judge.execution.ExecutionResult;
// // import com.codearena.backend.service.TestcaseService;
// // import org.springframework.stereotype.Service;

// // import java.util.List;

// // @Service
// // public class CodeJudgeService {

// //     private final TestcaseService testcaseService;
// //     private final CodeExecutionService codeExecutionService;

// //     public CodeJudgeService(TestcaseService testcaseService,
// //                             CodeExecutionService codeExecutionService) {
// //         this.testcaseService = testcaseService;
// //         this.codeExecutionService = codeExecutionService;
// //     }

// //     // RUN mode → publicOnly = true
// //     // SUBMIT mode → publicOnly = false
// //     public JudgeResponse judgeJava(Long problemId,
// //                                    String userCode,
// //                                    boolean publicOnly) {

// //         List<Testcase> testcases = publicOnly
// //                 ? testcaseService.getPublicTestCases(problemId)
// //                 : testcaseService.getTestCasesForProblem(problemId);

// //         for (int i = 0; i < testcases.size(); i++) {

// //             Testcase tc = testcases.get(i);
// //             int testcaseNo = i + 1; // ✅ 1-based index like LeetCode

// //             String driverCode = JavaDriverGenerator.generate(tc, problemId);

// //             ExecutionResult exec =
// //                     codeExecutionService.executeJava(userCode, driverCode);

// //             // ❌ Compilation Error (no testcase index needed)
// //             if (exec.isCompileError()) {
// //                 JudgeResponse res = new JudgeResponse(JudgeResult.COMPILATION_ERROR);
// //                 res.setMessage(exec.getOutput());
// //                 return res;
// //             }

// //             // ❌ Runtime Error
// //             if (exec.isRuntimeError()) {
// //                 JudgeResponse res = new JudgeResponse(JudgeResult.RUNTIME_ERROR);
// //                 res.setMessage(exec.getOutput());
// //                 res.setInput(tc.getInputJson());
// //                 res.setFailedTestcaseIndex(testcaseNo); // ✅ added
// //                 return res;
// //             }

// //             // Compare output
// //             String actual = normalize(exec.getOutput());
// //             String expected = normalize(tc.getOutputJson());

// //             // ❌ Wrong Answer
// //             if (!actual.equals(expected)) {
// //                 JudgeResponse res = new JudgeResponse(JudgeResult.WRONG_ANSWER);
// //                 res.setInput(tc.getInputJson());
// //                 res.setExpectedOutput(tc.getOutputJson());
// //                 res.setActualOutput(exec.getOutput());
// //                 res.setFailedTestcaseIndex(testcaseNo); // ✅ added
// //                 return res;
// //             }
// //         }

// //         // ✅ Accepted
// //         return new JudgeResponse(JudgeResult.ACCEPTED);
// //     }

// //     private String normalize(String s) {
// //         if (s == null) return "";

// //         s = s.trim();
// //         s = s.replace("\r\n", "\n");
// //         s = s.replaceAll("\\s+", "");

// //         return s;
// //     }
// // }








// package com.codearena.backend.judge;

// import com.codearena.backend.dto.JudgeResponse;
// import com.codearena.backend.entity.Testcase;
// import com.codearena.backend.judge.driver.JavaDriverGenerator;
// import com.codearena.backend.judge.execution.CodeExecutionService;
// import com.codearena.backend.judge.execution.ExecutionResult;
// import com.codearena.backend.service.TestcaseService;
// import org.springframework.stereotype.Service;

// import java.util.List;

// @Service
// public class CodeJudgeService {

//     private final TestcaseService testcaseService;
//     private final CodeExecutionService codeExecutionService;

//     public CodeJudgeService(TestcaseService testcaseService,
//                             CodeExecutionService codeExecutionService) {
//         this.testcaseService = testcaseService;
//         this.codeExecutionService = codeExecutionService;
//     }

//     public JudgeResponse judgeJava(Long problemId,
//                                    String userCode,
//                                    boolean publicOnly) {

//         List<Testcase> testcases = publicOnly
//                 ? testcaseService.getPublicTestCases(problemId)
//                 : testcaseService.getTestCasesForProblem(problemId);

//         if (testcases.isEmpty()) {
//             JudgeResponse res = new JudgeResponse(JudgeResult.RUNTIME_ERROR);
//             res.setMessage("No testcases found for this problem.");
//             return res;
//         }

//         CodeExecutionService.JavaSession session = null;

//         try {
//             // ✅ 1️⃣ Generate FULL Main.java (includes user code)
//             String fullCode = JavaDriverGenerator.generate(problemId, userCode);

//             // ✅ 2️⃣ Compile ONCE
//             session = codeExecutionService.compileJavaOnce(fullCode);

//             // ✅ 3️⃣ Run for each testcase
//             for (int i = 0; i < testcases.size(); i++) {

//                 Testcase tc = testcases.get(i);
//                 int testcaseNo = i + 1;

//                 ExecutionResult exec =
//                         codeExecutionService.runJava(session, tc.getInputJson());

//                 // Runtime Error
//                 if (exec.isRuntimeError()) {
//                     JudgeResponse res = new JudgeResponse(JudgeResult.RUNTIME_ERROR);
//                     res.setMessage(exec.getOutput());
//                     res.setInput(tc.getInputJson());
//                     res.setFailedTestcaseIndex(testcaseNo);
//                     return res;
//                 }

//                 String actual = normalize(exec.getOutput());
//                 String expected = normalize(tc.getOutputJson());

//                 // Wrong Answer
//                 if (!actual.equals(expected)) {
//                     JudgeResponse res = new JudgeResponse(JudgeResult.WRONG_ANSWER);
//                     res.setInput(tc.getInputJson());
//                     res.setExpectedOutput(tc.getOutputJson());
//                     res.setActualOutput(exec.getOutput());
//                     res.setFailedTestcaseIndex(testcaseNo);
//                     return res;
//                 }
//             }

//             // All passed
//             return new JudgeResponse(JudgeResult.ACCEPTED);

//         } catch (Exception e) {
//             // Compilation Error
//             JudgeResponse res = new JudgeResponse(JudgeResult.COMPILATION_ERROR);
//             res.setMessage(e.getMessage());
//             return res;

//         } finally {
//             // Cleanup temp folder
//             if (session != null) {
//                 codeExecutionService.cleanup(session);
//             }
//         }
//     }

//     private String normalize(String s) {
//         // if (s == null) return "";

//         // s = s.trim();

//         // // normalize new lines
//         // s = s.replace("\r\n", "\n");
//         // s = s.replace("\n", "");

//         // // remove all spaces/tabs
//         // s = s.replaceAll("\\s+", "");

//         // // normalize booleans
//         // if (s.equalsIgnoreCase("true")) return "true";
//         // if (s.equalsIgnoreCase("false")) return "false";

//         // // normalize null keyword
//         // s = s.replace("NULL", "null");

//         // // fix trailing comma in list like [1,2,3,]
//         // if (s.startsWith("[") && s.endsWith("]")) {
//         //     s = s.replace(",]", "]");
//         // }


//         if (s == null) return "";
//         s = s.trim();

//         // remove surrounding quotes
//         if (s.startsWith("\"") && s.endsWith("\"")) {
//             s = s.substring(1, s.length() - 1);
//         }

//         // normalize new lines
//         s = s.replace("\r\n", "\n");
//         s = s.replace("\n", "");

//         // remove spaces/tabs
//         s = s.replaceAll("\\s+", "");

   
//         return s;
//     }
// }




















































package com.codearena.backend.judge;

import com.codearena.backend.dto.JudgeResponse;
import com.codearena.backend.entity.Submission;
import com.codearena.backend.entity.Testcase;
import com.codearena.backend.judge.driver.JavaDriverGenerator;
import com.codearena.backend.judge.execution.CodeExecutionService;
import com.codearena.backend.judge.execution.ExecutionResult;
import com.codearena.backend.repository.SubmissionRepository;
import com.codearena.backend.service.TestcaseService;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CodeJudgeService {

    private final TestcaseService testcaseService;
    private final CodeExecutionService codeExecutionService;
    private final SubmissionRepository submissionRepository;

    public CodeJudgeService(TestcaseService testcaseService,
                            CodeExecutionService codeExecutionService,
                            SubmissionRepository submissionRepository) {
        this.testcaseService = testcaseService;
        this.codeExecutionService = codeExecutionService;
        this.submissionRepository = submissionRepository;
    }

    public JudgeResponse judgeJava(Long problemId,
                                   String userCode,
                                   boolean publicOnly,
                                   Long userId) {

        List<Testcase> testcases = publicOnly
                ? testcaseService.getPublicTestCases(problemId)
                : testcaseService.getTestCasesForProblem(problemId);

        if (testcases.isEmpty()) {
            JudgeResponse res = new JudgeResponse(JudgeResult.RUNTIME_ERROR);
            res.setMessage("No testcases found.");
            return res;
        }

        CodeExecutionService.JavaSession session = null;

        try {
            // ✅ Generate full driver code
            String fullCode = JavaDriverGenerator.generate(problemId, userCode);

            // ✅ Compile once
            session = codeExecutionService.compileJavaOnce(fullCode);

            // ✅ Run all testcases
            for (int i = 0; i < testcases.size(); i++) {

                Testcase tc = testcases.get(i);
                int testcaseNo = i + 1;

                ExecutionResult exec =
                        codeExecutionService.runJava(session, tc.getInputJson());

                // 🔴 Runtime Error
                if (exec.isRuntimeError()) {

                    saveSubmission(userId, problemId, userCode, "RUNTIME_ERROR");

                    JudgeResponse res = new JudgeResponse(JudgeResult.RUNTIME_ERROR);
                    res.setMessage(exec.getOutput());
                    res.setInput(tc.getInputJson());
                    res.setFailedTestcaseIndex(testcaseNo);
                    return res;
                }

                String actual = normalize(exec.getOutput());
                String expected = normalize(tc.getOutputJson());

                // 🔴 Wrong Answer
                if (!actual.equals(expected)) {

                    saveSubmission(userId, problemId, userCode, "WRONG_ANSWER");

                    JudgeResponse res = new JudgeResponse(JudgeResult.WRONG_ANSWER);
                    res.setInput(tc.getInputJson());
                    res.setExpectedOutput(expected);
                    res.setActualOutput(actual);
                    res.setFailedTestcaseIndex(testcaseNo);
                    return res;
                }
            }

            // ✅ Accepted
            saveSubmission(userId, problemId, userCode, "ACCEPTED");

            return new JudgeResponse(JudgeResult.ACCEPTED);

        } catch (Exception e) {

            // 🔴 Compilation Error
            saveSubmission(userId, problemId, userCode, "COMPILATION_ERROR");

            JudgeResponse res = new JudgeResponse(JudgeResult.COMPILATION_ERROR);
            res.setMessage(e.getMessage());
            return res;

        } finally {
            if (session != null) {
                codeExecutionService.cleanup(session);
            }
        }
    }

    /* -------------------- SAVE SUBMISSION -------------------- */

    private void saveSubmission(Long userId,
                                Long problemId,
                                String code,
                                String verdict) {

        Submission submission = new Submission();
        submission.setUserId(userId);
        submission.setProblemId(problemId);
        submission.setLanguage("JAVA");
        submission.setCode(code);
        submission.setVerdict(verdict);

        submissionRepository.save(submission);
    }

    /* -------------------- NORMALIZE OUTPUT -------------------- */

    private String normalize(String s) {
        if (s == null) return "";

        s = s.trim();

        // remove quotes
        if (s.startsWith("\"") && s.endsWith("\"")) {
            s = s.substring(1, s.length() - 1);
        }

        // normalize new lines
        s = s.replace("\r\n", "\n");
        s = s.replace("\n", "");

        // remove all spaces
        s = s.replaceAll("\\s+", "");

        // fix trailing comma like [1,2,]
        if (s.startsWith("[") && s.endsWith("]")) {
            s = s.replace(",]", "]");
        }

        return s;
    }
}