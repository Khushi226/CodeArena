// package com.codearena.backend.controller;

// import com.codearena.backend.dto.LeetCodeProblemDTO;
// import com.codearena.backend.service.ProblemImportService;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import org.springframework.core.io.ClassPathResource;
// import org.springframework.web.bind.annotation.*;

// @RestController
// @RequestMapping("/import")
// public class ProblemImportController {

//     private final ProblemImportService importService;
//     private final ObjectMapper mapper;

//     public ProblemImportController(
//         ProblemImportService importService,
//         ObjectMapper mapper
//     ) {
//         this.importService = importService;
//         this.mapper = mapper;
//     }

//     @PostMapping
//     public String importProblem() throws Exception {

//         ClassPathResource resource =
//             new ClassPathResource("problems/0001-two-sum.json");

//         LeetCodeProblemDTO dto =
//             mapper.readValue(resource.getInputStream(),
//                              LeetCodeProblemDTO.class);

//         importService.importFromJson(dto);

//         return "Problem imported successfully";
//     }
// }







// package com.codearena.backend.controller;

// import com.codearena.backend.dto.LeetCodeProblemDTO;
// import com.codearena.backend.service.ProblemImportService;
// import org.springframework.web.bind.annotation.*;

// @RestController
// @RequestMapping("/import")
// public class ProblemImportController {

//     private final ProblemImportService importService;

//     public ProblemImportController(ProblemImportService importService) {
//         this.importService = importService;
//     }

//     @PostMapping
//     public String importProblem(@RequestBody LeetCodeProblemDTO dto) {
//         importService.importFromJson(dto);
//         return "Problem '" + dto.getTitle() + "' imported successfully!";
//     }
// }


package com.codearena.backend.controller;

import com.codearena.backend.dto.LeetCodeProblemDTO;
import com.codearena.backend.service.ProblemImportService;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.core.io.Resource;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/import")
public class ProblemImportController {

    private final ProblemImportService importService;
    private final ObjectMapper mapper;

    public ProblemImportController(ProblemImportService importService, ObjectMapper mapper) {
        this.importService = importService;
        this.mapper = mapper;
    }

    @PostMapping("/all")
    public String importAllLocalFiles() throws Exception {
        org.springframework.core.io.support.PathMatchingResourcePatternResolver resolver = 
            new org.springframework.core.io.support.PathMatchingResourcePatternResolver();
        
        // Scans src/main/resources/problems/ for all .json files
        org.springframework.core.io.Resource[] resources = resolver.getResources("classpath:problems/*.json");

        int count = 0;
        for (org.springframework.core.io.Resource resource : resources) {
            LeetCodeProblemDTO dto = mapper.readValue(resource.getInputStream(), LeetCodeProblemDTO.class);
            importService.importFromJson(dto);
            count++;
        }
        return "Successfully imported " + count + " problems.";
    }
}