package com.codearena.backend.service;

import com.codearena.backend.entity.Submission;
import com.codearena.backend.repository.SubmissionRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class SubmissionService {

    private final SubmissionRepository submissionRepository;

    public SubmissionService(SubmissionRepository submissionRepository) {
        this.submissionRepository = submissionRepository;
    }

    // Save a submission
    public Submission saveSubmission(Submission submission) {
        return submissionRepository.save(submission);
    }

    // Get submissions for a user (latest first)
    public List<Submission> getSubmissionsByUser(Long userId) {
        return submissionRepository.findByUserId(userId);
    }

    // ✅ Get submissions for a problem (latest first)
    public List<Submission> getSubmissionsByProblem(Long problemId) {
        return submissionRepository.findByProblemIdOrderByIdDesc(problemId);
    }

    // ✅ Get submissions for a user on a problem (latest first)
    public List<Submission> getUserSubmissionsForProblem(Long userId, Long problemId) {
        return submissionRepository.findByUserIdAndProblemIdOrderByIdDesc(userId, problemId);
    }
}
