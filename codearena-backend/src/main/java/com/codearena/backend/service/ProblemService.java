
// package com.codearena.backend.service;

// import com.codearena.backend.entity.Problem;
// import com.codearena.backend.repository.ProblemRepository;
// import org.springframework.stereotype.Service;

// import java.util.List;

// @Service
// public class ProblemService {

//     private final ProblemRepository repository;

//     public ProblemService(ProblemRepository repository) {
//         this.repository = repository;
//     }

//     /**
//      * Fetch all problems (Problems list page)
//      */
//     public List<Problem> getAllProblems() {
//         return repository.findAll();
//     }

//     /**
//      * Fetch a single problem by slug (Problem detail page)
//      */
//     public Problem getProblemBySlug(String slug) {
//         return repository
//                 .findByProblemSlug(slug)
//                 .orElseThrow(() ->
//                         new RuntimeException("Problem not found with slug: " + slug)
//                 );
//     }

//     public List<Problem> getProblemsByTopic(String topic) {
//         return repository.findByTopic(topic);
//     }

//     public Problem getProblemById(Long id) {
//         return repository.findById(id)
//                 .orElseThrow(() -> new RuntimeException("Problem not found with id: " + id));
//     }
// }














package com.codearena.backend.service;

import com.codearena.backend.entity.Problem;
import com.codearena.backend.repository.ProblemRepository;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ProblemService {

    private final ProblemRepository repository;

    public ProblemService(ProblemRepository repository) {
        this.repository = repository;
    }

    /**
     * Fetch all VISIBLE problems (public Problems list page).
     */
    public List<Problem> getAllProblems() {
        return repository.findByVisibleTrue();
    }

    /**
     * Every problem regardless of visibility — for the admin contest-builder
     * only. Never expose this through a permitAll() route.
     */
    public List<Problem> getAllProblemsForAdmin() {
        return repository.findAllByOrderByIdAsc();
    }

    /**
     * Fetch a single VISIBLE problem by slug (public Problem detail page).
     * A contest-exclusive problem 404s here even if you know its slug —
     * it's only reachable via /contests/{id}/problems/{problemId} while
     * its contest is running, and through this method once revealed.
     */
    public Problem getProblemBySlug(String slug) {
        return repository
                .findByProblemSlugAndVisibleTrue(slug)
                .orElseThrow(() ->
                        new RuntimeException("Problem not found with slug: " + slug)
                );
    }

    public List<Problem> getProblemsByTopic(String topic) {
        return repository.findByTopic(topic);
    }

    /**
     * Unfiltered by design — used by ContestService.getContestProblem(),
     * which does its own contest-start-time gate and must work regardless
     * of whether the problem is publicly visible yet.
     */
    public Problem getProblemById(Long id) {
        return repository.findById(id)
                .orElseThrow(() -> new RuntimeException("Problem not found with id: " + id));
    }

    public List<Problem> getHiddenProblemsForAdmin() {
        return repository.findByVisibleFalse();
    }
}