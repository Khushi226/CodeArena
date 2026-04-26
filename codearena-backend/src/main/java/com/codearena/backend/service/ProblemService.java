// package com.codearena.backend.service;

// import org.springframework.stereotype.Service;
// import java.util.List;
// import com.codearena.backend.entity.Problem;
// import com.codearena.backend.repository.ProblemRepository;

// @Service
// public class ProblemService {

//     private final ProblemRepository repository;

//     public ProblemService(ProblemRepository repository) {
//         this.repository = repository;
//     }

//     public List<Problem> getAllProblems() {
//         return repository.findAll();
//     }

//     public Problem getProblemById(Long id) {
//         return repository.findById(id)
//             .orElseThrow(() -> new RuntimeException("Problem not found"));
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
     * Fetch all problems (Problems list page)
     */
    public List<Problem> getAllProblems() {
        return repository.findAll();
    }

    /**
     * Fetch a single problem by slug (Problem detail page)
     */
    public Problem getProblemBySlug(String slug) {
        return repository
                .findByProblemSlug(slug)
                .orElseThrow(() ->
                        new RuntimeException("Problem not found with slug: " + slug)
                );
    }



    public List<Problem> getProblemsByTopic(String topic) {
        return repository.findByTopic(topic);
    }
}
