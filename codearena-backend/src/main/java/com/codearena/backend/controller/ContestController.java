package com.codearena.backend.controller;

import com.codearena.backend.dto.*;
import com.codearena.backend.service.ContestService;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/contests")
@CrossOrigin(origins = "http://localhost:5173")
public class ContestController {

    private final ContestService contestService;

    public ContestController(ContestService contestService) {
        this.contestService = contestService;
    }

    @GetMapping
    public List<ContestResponse> getAllContests() {
        return contestService.getAll(getUserIdOrNull());
    }

    @GetMapping("/{slug}")
    public ContestResponse getContest(@PathVariable String slug) {
        return contestService.getBySlug(slug, getUserIdOrNull());
    }

    // Admin only. Requires User.role = ADMIN — see SecurityConfig for the matcher.
    @PostMapping
    public ContestResponse createContest(@RequestBody ContestCreateRequest request) {
        return contestService.createContest(request, requireUserId());
    }

    @PostMapping("/{id}/register")
    public void register(@PathVariable Long id) {
        contestService.register(id, requireUserId());
    }

    @GetMapping("/{id}/problems/{problemId}")
    public ProblemResponse getContestProblem(@PathVariable Long id, @PathVariable Long problemId) {
        return contestService.getContestProblem(id, problemId, requireUserId());
    }

    @GetMapping("/{id}/problems/{problemId}/submissions")
    public List<com.codearena.backend.entity.Submission> getMySubmissions(
            @PathVariable Long id,
            @PathVariable Long problemId
    ) {
        return contestService.getMySubmissionsForProblem(id, problemId, requireUserId());
    }

    @PostMapping("/{id}/submit")
    public JudgeResponse submit(@PathVariable Long id, @RequestBody ContestSubmitRequest request) {
        return contestService.submit(id, request, requireUserId());
    }

    @GetMapping("/{id}/leaderboard")
    public List<LeaderboardEntry> getLeaderboard(@PathVariable Long id) {
        return contestService.getLeaderboard(id);
    }

    /* -------------------- auth helpers, same pattern as SubmitController -------------------- */

    private Long requireUserId() {
        Long userId = getUserIdOrNull();
        if (userId == null) {
            throw new RuntimeException("Unauthorized: userId not found in token");
        }
        return userId;
    }

    private Long getUserIdOrNull() {
        if (SecurityContextHolder.getContext().getAuthentication() != null) {
            Object details = SecurityContextHolder.getContext().getAuthentication().getDetails();
            if (details instanceof Long) {
                return (Long) details;
            }
        }
        return null;
    }
}