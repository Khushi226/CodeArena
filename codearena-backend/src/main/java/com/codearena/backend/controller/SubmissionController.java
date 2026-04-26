package com.codearena.backend.controller;

import com.codearena.backend.entity.Submission;
import com.codearena.backend.service.SubmissionService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/submissions")
public class SubmissionController {

    private final SubmissionService submissionService;

    public SubmissionController(SubmissionService submissionService) {
        this.submissionService = submissionService;
    }

    // ✅ Get all submissions for a problem (latest first)
    @GetMapping("/problem/{problemId}")
    public List<Submission> getSubmissionsForProblem(@PathVariable Long problemId) {
        return submissionService.getSubmissionsByProblem(problemId);
    }

    // ✅ Get all submissions for a user (latest first)
    @GetMapping("/user/{userId}")
    public List<Submission> getSubmissionsForUser(@PathVariable Long userId) {
        return submissionService.getSubmissionsByUser(userId);
    }

    // ✅ Get submissions for a user on a specific problem (latest first)
    @GetMapping("/user/{userId}/problem/{problemId}")
    public List<Submission> getUserSubmissionsForProblem(
            @PathVariable Long userId,
            @PathVariable Long problemId
    ) {
        return submissionService.getUserSubmissionsForProblem(userId, problemId);
    }
}
