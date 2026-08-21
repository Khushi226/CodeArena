


package com.codearena.backend.service;

import com.codearena.backend.dto.LeetCodeProblemDTO;
import com.codearena.backend.dto.MethodParamDTO;
import com.codearena.backend.dto.TestcaseDTO;
import com.codearena.backend.entity.Problem;
import com.codearena.backend.entity.ProblemMetaEntity;
import com.codearena.backend.entity.Testcase;
import com.codearena.backend.judge.driver.ProblemMeta;
import com.codearena.backend.judge.driver.ProblemMetaRegistry;
import com.codearena.backend.repository.ProblemMetaRepository;
import com.codearena.backend.repository.ProblemRepository;
import com.codearena.backend.repository.TestcaseRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ProblemImportService {

    private final ProblemRepository problemRepository;
    private final ProblemMetaRepository problemMetaRepository;
    private final TestcaseRepository testcaseRepository;
    private final ObjectMapper objectMapper;

    public ProblemImportService(ProblemRepository problemRepository,
                                 ProblemMetaRepository problemMetaRepository,
                                 TestcaseRepository testcaseRepository,
                                 ObjectMapper objectMapper) {
        this.problemRepository = problemRepository;
        this.problemMetaRepository = problemMetaRepository;
        this.testcaseRepository = testcaseRepository;
        this.objectMapper = objectMapper;
    }

    public void importFromJson(LeetCodeProblemDTO dto) {
        // /import/all rescans every file in problems/ every time it's called —
        // this upsert is what makes that safe AND correct to run repeatedly.
        // Skipping re-imports entirely once a slug existed stopped crashes
        // but also silently ignored real edits to the JSON (e.g. flipping
        // visible: true -> false never took effect). An existing row is
        // updated in place instead of skipped or duplicated.
        Problem problem = problemRepository.findByProblemSlug(dto.getProblem_slug())
                .orElseGet(Problem::new);
        boolean isNewProblem = problem.getId() == null;

        problem.setTitle(dto.getTitle());
        problem.setProblemSlug(dto.getProblem_slug());
        problem.setDifficulty(dto.getDifficulty());
        problem.setDescription(dto.getDescription());
        problem.setTopics(String.join(",", dto.getTopics()));
        problem.setConstraints(String.join("\n", dto.getConstraints()));

        try {
            problem.setExamples(objectMapper.writeValueAsString(dto.getExamples()));
        } catch (Exception e) {
            problem.setExamples("[]");
        }

        problem.setVisible(dto.getVisible() == null ? true : dto.getVisible());

        problem = problemRepository.save(problem); // insert if new, update if existing

        if (dto.getMethodSignature() != null) {
            saveMethodSignature(problem.getId(), dto.getMethodSignature());
        }

        if (dto.getTestcases() != null) {
            // Testcase has its own auto-increment id unrelated to problemId,
            // so re-running saveTestcases() on an existing problem without
            // clearing first would duplicate every test case rather than
            // update them. Clear-then-reinsert makes the JSON the single
            // source of truth on every import.
            testcaseRepository.deleteByProblemId(problem.getId());
            saveTestcases(problem.getId(), dto.getTestcases());
        }

        System.out.println((isNewProblem ? "✅ Imported new problem: " : "🔄 Updated existing problem: ")
                + dto.getProblem_slug());
    }

    private void saveTestcases(Long problemId, List<TestcaseDTO> testcaseDtos) {
        for (TestcaseDTO tc : testcaseDtos) {
            Testcase entity = new Testcase();
            entity.setProblemId(problemId);

            try {
                entity.setInputJson(objectMapper.writeValueAsString(tc.getInput()));
            } catch (Exception e) {
                entity.setInputJson("{}");
            }

            String rawOutput = tc.getOutput() != null ? tc.getOutput() : "";
            String storedOutput;
            try {
                objectMapper.readTree(rawOutput);
                storedOutput = rawOutput;
            } catch (Exception e) {
                try {
                    storedOutput = objectMapper.writeValueAsString(rawOutput);
                } catch (com.fasterxml.jackson.core.JsonProcessingException ex) {
                    storedOutput = "\"\"";
                }
            }
            entity.setOutputJson(storedOutput);

            Testcase.Visibility visibility;
            try {
                visibility = tc.getVisibility() != null
                        ? Testcase.Visibility.valueOf(tc.getVisibility().toUpperCase())
                        : Testcase.Visibility.PRIVATE;
            } catch (IllegalArgumentException e) {
                visibility = Testcase.Visibility.PRIVATE;
            }
            entity.setVisibility(visibility);

            testcaseRepository.save(entity);
        }
    }

    private void saveMethodSignature(Long problemId, com.codearena.backend.dto.MethodSignatureDTO sig) {
        List<MethodParamDTO> paramDtos = sig.getParams() != null ? sig.getParams() : new ArrayList<>();

        List<ProblemMeta.Param> params = new ArrayList<>();
        for (MethodParamDTO p : paramDtos) {
            params.add(new ProblemMeta.Param(p.getName(), p.getType()));
        }

        ProblemMetaEntity entity = new ProblemMetaEntity();
        entity.setProblemId(problemId);
        entity.setMethodName(sig.getMethodName());
        entity.setReturnType(sig.getReturnType());
        entity.setOutputParam(sig.getOutputParam());
        try {
            entity.setParamsJson(objectMapper.writeValueAsString(paramDtos));
        } catch (Exception e) {
            entity.setParamsJson("[]");
        }
        problemMetaRepository.save(entity);

        ProblemMetaRegistry.put(problemId,
                new ProblemMeta(sig.getMethodName(), sig.getReturnType(), params, sig.getOutputParam()));
    }
}