// package com.codearena.backend.service;

// import com.codearena.backend.dto.LeetCodeProblemDTO;
// import com.codearena.backend.entity.Problem;
// import com.codearena.backend.repository.ProblemRepository;
// import org.springframework.stereotype.Service;

// @Service
// public class ProblemImportService {

//     private final ProblemRepository problemRepository;

//     public ProblemImportService(ProblemRepository problemRepository) {
//         this.problemRepository = problemRepository;
//     }

//     public void importFromJson(LeetCodeProblemDTO dto) {

//         Problem problem = new Problem();
//         problem.setTitle(dto.getTitle());
//         problem.setProblemSlug(dto.getProblem_slug());
//         problem.setDifficulty(dto.getDifficulty());
//         problem.setDescription(dto.getDescription());
//         problem.setTopics(String.join(",", dto.getTopics()));
//         problem.setConstraints(String.join("\n", dto.getConstraints()));

//         problemRepository.save(problem);
//     }
// }






// package com.codearena.backend.service;

// import com.codearena.backend.dto.LeetCodeProblemDTO;
// import com.codearena.backend.entity.Problem;
// import com.codearena.backend.repository.ProblemRepository;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import org.springframework.stereotype.Service;

// @Service
// public class ProblemImportService {

//     private final ProblemRepository problemRepository;
//     private final ObjectMapper objectMapper; 

//     public ProblemImportService(ProblemRepository problemRepository, ObjectMapper objectMapper) {
//         this.problemRepository = problemRepository;
//         this.objectMapper = objectMapper;
//     }

//     public void importFromJson(LeetCodeProblemDTO dto) {
//         Problem problem = new Problem();
//         problem.setTitle(dto.getTitle());
//         problem.setProblemSlug(dto.getProblem_slug()); // From problem_slug
//         problem.setDifficulty(dto.getDifficulty());
//         problem.setDescription(dto.getDescription());
        
//         // Convert arrays to strings for DB storage
//         problem.setTopics(String.join(", ", dto.getTopics()));
//         problem.setConstraints(String.join("\n", dto.getConstraints()));

//         try {
//             // Store the list of examples as a JSON string
//             problem.setExamples(objectMapper.writeValueAsString(dto.getExamples()));
//         } catch (Exception e) {
//             problem.setExamples("[]");
//         }


//         problemRepository.save(problem);
//     }

//     public void importAllFromList(List<LeetCodeProblemDTO> dtos) {
//         for (LeetCodeProblemDTO dto : dtos) {
//             importFromJson(dto); // Reuse your existing single-import logic
//         }
//     }
// }



package com.codearena.backend.service;

import com.codearena.backend.dto.LeetCodeProblemDTO;
import com.codearena.backend.entity.Problem;
import com.codearena.backend.repository.ProblemRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

@Service
public class ProblemImportService {

    private final ProblemRepository problemRepository;
    private final ObjectMapper objectMapper; // Add Jackson mapper

    public ProblemImportService(ProblemRepository problemRepository, ObjectMapper objectMapper) {
        this.problemRepository = problemRepository;
        this.objectMapper = objectMapper;
    }

    public void importFromJson(LeetCodeProblemDTO dto) {
        Problem problem = new Problem();
        problem.setTitle(dto.getTitle());
        problem.setProblemSlug(dto.getProblem_slug());
        problem.setDifficulty(dto.getDifficulty());
        problem.setDescription(dto.getDescription());
        problem.setTopics(String.join(",", dto.getTopics()));
        problem.setConstraints(String.join("\n", dto.getConstraints()));

        try {
            // Converts the list of examples into a JSON string for the DB
            problem.setExamples(objectMapper.writeValueAsString(dto.getExamples()));
        } catch (Exception e) {
            problem.setExamples("[]");
        }

        problemRepository.save(problem);
    }
}