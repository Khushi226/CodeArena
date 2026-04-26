package com.codearena.backend.service;

import com.codearena.backend.dto.DashboardResponse;
import com.codearena.backend.entity.User;
import com.codearena.backend.repository.SubmissionRepository;
import com.codearena.backend.repository.UserRepository;
import com.codearena.backend.security.JwtUtil;

import org.springframework.stereotype.Service;

@Service
public class DashboardService {

    private final UserRepository userRepository;
    private final SubmissionRepository submissionRepository;
    private final JwtUtil jwtService;

    public DashboardService(UserRepository userRepository,
                            SubmissionRepository submissionRepository,
                            JwtUtil jwtService) {
        this.userRepository = userRepository;
        this.submissionRepository = submissionRepository;
        this.jwtService = jwtService;
    }

    public DashboardResponse getDashboard(String token) {

        // Extract username from JWT
        String username = jwtService.extractUsername(token);

        // Find user
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Long userId = user.getId();

        // Count solved problems
        long total = submissionRepository.countDistinctAccepted(userId);
        long easy = submissionRepository.countAcceptedByDifficulty(userId, "Easy");
        long medium = submissionRepository.countAcceptedByDifficulty(userId, "Medium");
        long hard = submissionRepository.countAcceptedByDifficulty(userId, "Hard");

        return new DashboardResponse(
                user.getUsername(),
                user.getEmail(),
                total,
                easy,
                medium,
                hard
        );
    }
}