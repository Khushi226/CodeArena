// package com.codearena.backend.controller;

// import com.codearena.backend.dto.JudgeResponse;
// import com.codearena.backend.dto.SubmissionRequest;
// import com.codearena.backend.entity.Submission;
// import com.codearena.backend.judge.CodeJudgeService;
// import com.codearena.backend.service.SubmissionService;
// import org.springframework.web.bind.annotation.*;

// @RestController
// @RequestMapping("/submit")
// public class SubmitController {

//     private final SubmissionService submissionService;
//     private final CodeJudgeService codeJudgeService;

//     public SubmitController(SubmissionService submissionService,
//                             CodeJudgeService codeJudgeService) {
//         this.submissionService = submissionService;
//         this.codeJudgeService = codeJudgeService;
//     }

//     @PostMapping
//     public JudgeResponse submitCode(@RequestBody SubmissionRequest request) {

//         // SUBMIT → ALL testcases
//         JudgeResponse response = codeJudgeService.judgeJava(
//                 request.getProblemId(),
//                 request.getCode(),
//                 false
//         );

//         // Save submission
//         Submission submission = new Submission();
//         submission.setUserId(1L); // hardcoded for now
//         submission.setProblemId(request.getProblemId());
//         submission.setLanguage(request.getLanguage());
//         submission.setCode(request.getCode());
//         submission.setVerdict(response.getResult().name());

//         submissionService.saveSubmission(submission);

//         return response;
//     }
// }



package com.codearena.backend.controller;

import com.codearena.backend.dto.RunRequest;
import com.codearena.backend.entity.Submission;
import com.codearena.backend.judge.CodeJudgeService;
import com.codearena.backend.dto.JudgeResponse;
import com.codearena.backend.service.SubmissionService;

import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/submit")
@CrossOrigin(origins = "http://localhost:5173")
public class SubmitController {

    private final CodeJudgeService codeJudgeService;
    private final SubmissionService submissionService;

    public SubmitController(CodeJudgeService codeJudgeService,
                            SubmissionService submissionService) {
        this.codeJudgeService = codeJudgeService;
        this.submissionService = submissionService;
    }

    @PostMapping
    public JudgeResponse submitCode(@RequestBody RunRequest request) {

        // ✅ get logged-in userId from JWT
        Long userId = null;

        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();
            if (details instanceof Long) {
                userId = (Long) details;
            }
        }

        if (userId == null) {
            throw new RuntimeException("Unauthorized: userId not found in token");
        }

        // 1️⃣ Judge on ALL testcases
        JudgeResponse result =
                codeJudgeService.judgeJava(
                        request.getProblemId(),
                        request.getCode(),
                        false,
                        1L
                );

        // 2️⃣ Save submission
        Submission submission = new Submission();
        submission.setUserId(userId); // ✅ real userId
        submission.setProblemId(request.getProblemId());
        submission.setLanguage(request.getLanguage());
        submission.setCode(request.getCode());
        submission.setVerdict(result.getResult().name());

        submissionService.saveSubmission(submission);

        // 3️⃣ Return response
        return result;
    }
}
