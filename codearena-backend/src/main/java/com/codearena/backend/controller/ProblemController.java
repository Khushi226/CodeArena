


package com.codearena.backend.controller;

import com.codearena.backend.dto.ProblemResponse;
import com.codearena.backend.entity.Problem;
import com.codearena.backend.judge.driver.StarterCodeGenerator;
import com.codearena.backend.service.ProblemService;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/problems")
@CrossOrigin(origins = "http://localhost:5173")
public class ProblemController {

    private final ProblemService problemService;

    public ProblemController(ProblemService problemService) {
        this.problemService = problemService;
    }

    /**
     * GET /api/problems
     * Fetch all problems (Problems list page)
     */
    @GetMapping
    public List<Problem> getAllProblems() {
        return problemService.getAllProblems();
    }

    /**
     * GET /api/problems/{slug}
     * Fetch a single problem by slug (Problem detail page)
     */
    @GetMapping("/{slug}")
    public Problem getProblemBySlug(@PathVariable String slug) {
        return problemService.getProblemBySlug(slug);
    }


    @GetMapping("/problem/{slug}")
    public ProblemResponse getProblem(@PathVariable String slug) {

        Problem problem = problemService.getProblemBySlug(slug);

        // You still need problemId to generate starter code
        Long problemId = problem.getId();

        String starterCode = StarterCodeGenerator.generate(problemId);

        return new ProblemResponse(
            problem.getId(),
            problem.getTitle(),
            problem.getDescription(),
            problem.getDifficulty(),
            problem.getExamples(),
            problem.getConstraints(),
            starterCode
        );
    }






    @GetMapping("/topic/{topic}")
    public List<Problem> getProblemsByTopic(@PathVariable String topic) {
        return problemService.getProblemsByTopic(topic);
    }
}
