package com.codearena.backend.service;
import org.springframework.stereotype.Service;

import com.codearena.backend.entity.Testcase;
import com.codearena.backend.repository.TestcaseRepository;

import java.util.List;

@Service
public class TestcaseService {

    private final TestcaseRepository testCaseRepository;

    public TestcaseService(TestcaseRepository testCaseRepository) {
        this.testCaseRepository = testCaseRepository;
    }

    public List<Testcase> getTestCasesForProblem(Long problemId) {
        return testCaseRepository.findByProblemId(problemId);
    }
    public List<Testcase> getPublicTestCases(Long problemId) {
        return testCaseRepository
                .findByProblemIdAndVisibility(
                        problemId,
                        Testcase.Visibility.PUBLIC
                );
    }
}

