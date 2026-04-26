// package com.codearena.backend.controller;

// import com.codearena.backend.dto.JudgeResponse;
// import com.codearena.backend.dto.RunRequest;
// import com.codearena.backend.judge.CodeJudgeService;
// import org.springframework.web.bind.annotation.*;

// @RestController
// @RequestMapping("/run")
// public class RunController {

//     private final CodeJudgeService codeJudgeService;

//     public RunController(CodeJudgeService codeJudgeService) {
//         this.codeJudgeService = codeJudgeService;
//     }

//     @PostMapping
//     public JudgeResponse runCode(@RequestBody RunRequest request) {

//         // RUN → only PUBLIC testcases
//         return codeJudgeService.judgeJava(
//                 request.getProblemId(),
//                 request.getCode(),
//                 true
//         );
//     }
// }



package com.codearena.backend.controller;

import com.codearena.backend.dto.RunRequest;
import com.codearena.backend.dto.JudgeResponse;
import com.codearena.backend.judge.CodeJudgeService;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/run")
public class RunController {

    private final CodeJudgeService codeJudgeService;

    public RunController(CodeJudgeService codeJudgeService) {
        this.codeJudgeService = codeJudgeService;
    }

    @PostMapping
    public JudgeResponse runCode(@RequestBody RunRequest request) {

        // RUN = only public testcases
        return codeJudgeService.judgeJava(
                request.getProblemId(),
                request.getCode(),
                true,
                1L
        );
    }
}

