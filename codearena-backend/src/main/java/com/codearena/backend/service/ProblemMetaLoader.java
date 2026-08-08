// package com.codearena.backend.service;

// import com.codearena.backend.entity.ProblemMetaEntity;
// import com.codearena.backend.judge.driver.ProblemMeta;
// import com.codearena.backend.judge.driver.ProblemMetaRegistry;
// import com.codearena.backend.repository.ProblemMetaRepository;
// import com.fasterxml.jackson.core.type.TypeReference;
// import com.fasterxml.jackson.databind.ObjectMapper;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.stereotype.Component;

// import java.util.*;

// @Component
// public class ProblemMetaLoader implements CommandLineRunner {

//     private final ProblemMetaRepository repository;
//     private final ObjectMapper objectMapper;

//     public ProblemMetaLoader(ProblemMetaRepository repository, ObjectMapper objectMapper) {
//         this.repository = repository;
//         this.objectMapper = objectMapper;
//     }

//     @Override
//     public void run(String... args) throws Exception {
//         if (repository.count() == 0) {
//             seedLegacyEntries();
//         }
//         loadIntoRegistry();
//     }

//     // One-time migration of the 20 entries that used to live hand-typed in
//     // ProblemMetaRegistry.java's static block, so problems 1-20 keep working
//     // exactly as before without anyone touching the database by hand.
//     private void seedLegacyEntries() {
//         List<ProblemMetaEntity> legacy = new ArrayList<>();

//         legacy.add(entry(1L, "twoSum", "int[]",
//                 param("nums", "int[]"), param("target", "int")));
//         legacy.add(entry(2L, "addTwoNumbers", "ListNode",
//                 param("l1", "ListNode"), param("l2", "ListNode")));
//         legacy.add(entry(3L, "romanToInt", "int",
//                 param("s", "String")));
//         legacy.add(entry(4L, "longestCommonPrefix", "String",
//                 param("strs", "String[]")));
//         legacy.add(entry(5L, "isValid", "boolean",
//                 param("s", "String")));
//         legacy.add(entry(6L, "mergeTwoLists", "ListNode",
//                 param("list1", "ListNode"), param("list2", "ListNode")));
//         legacy.add(entry(7L, "searchInsert", "int",
//                 param("nums", "int[]"), param("target", "int")));
//         legacy.add(entry(8L, "maxSubArray", "int",
//                 param("nums", "int[]")));
//         legacy.add(entry(9L, "plusOne", "int[]",
//                 param("digits", "int[]")));
//         legacy.add(entry(10L, "merge", "void",
//                 param("nums1", "int[]"), param("m", "int"),
//                 param("nums2", "int[]"), param("n", "int")));
//         legacy.add(entry(11L, "inorderTraversal", "List<Integer>",
//                 param("root", "TreeNode")));
//         legacy.add(entry(12L, "isSameTree", "boolean",
//                 param("p", "TreeNode"), param("q", "TreeNode")));
//         legacy.add(entry(13L, "isSymmetric", "boolean",
//                 param("root", "TreeNode")));
//         legacy.add(entry(14L, "maxDepth", "int",
//                 param("root", "TreeNode")));
//         legacy.add(entry(15L, "maxProfit", "int",
//                 param("prices", "int[]")));
//         legacy.add(entry(16L, "isPalindrome", "boolean",
//                 param("s", "String")));
//         legacy.add(entry(17L, "hasCycle", "boolean",
//                 param("head", "ListNode")));
//         legacy.add(entry(18L, "reverseList", "ListNode",
//                 param("head", "ListNode")));
//         legacy.add(entry(19L, "invertTree", "TreeNode",
//                 param("root", "TreeNode")));
//         legacy.add(entry(20L, "isAnagram", "boolean",
//                 param("s", "String"), param("t", "String")));

//         repository.saveAll(legacy);
//     }

//     // Reads every row in problem_meta and populates the in-memory registry
//     // that StarterCodeGenerator/JavaDriverGenerator actually read from.
//     private void loadIntoRegistry() throws Exception {
//         Map<Long, ProblemMeta> data = new HashMap<>();

//         for (ProblemMetaEntity row : repository.findAll()) {
//             List<Map<String, String>> rawParams = objectMapper.readValue(
//                     row.getParamsJson(), new TypeReference<List<Map<String, String>>>() {}
//             );

//             List<ProblemMeta.Param> params = new ArrayList<>();
//             for (Map<String, String> p : rawParams) {
//                 params.add(new ProblemMeta.Param(p.get("name"), p.get("type")));
//             }

//             data.put(row.getProblemId(), new ProblemMeta(row.getMethodName(), row.getReturnType(), params));
//         }

//         ProblemMetaRegistry.load(data);
//         System.out.println("✅ Loaded " + data.size() + " problem signatures into ProblemMetaRegistry");
//     }

//     private Map<String, String> param(String name, String type) {
//         Map<String, String> p = new LinkedHashMap<>();
//         p.put("name", name);
//         p.put("type", type);
//         return p;
//     }

//     private ProblemMetaEntity entry(Long problemId, String methodName, String returnType,
//                                      @SuppressWarnings("unchecked") Map<String, String>... params) {
//         ProblemMetaEntity e = new ProblemMetaEntity();
//         e.setProblemId(problemId);
//         e.setMethodName(methodName);
//         e.setReturnType(returnType);
//         try {
//             e.setParamsJson(objectMapper.writeValueAsString(Arrays.asList(params)));
//         } catch (Exception ex) {
//             e.setParamsJson("[]");
//         }
//         return e;
//     }
// }












package com.codearena.backend.service;

import com.codearena.backend.entity.ProblemMetaEntity;
import com.codearena.backend.judge.driver.ProblemMeta;
import com.codearena.backend.judge.driver.ProblemMetaRegistry;
import com.codearena.backend.repository.ProblemMetaRepository;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class ProblemMetaLoader implements CommandLineRunner {

    private final ProblemMetaRepository repository;
    private final ObjectMapper objectMapper;

    public ProblemMetaLoader(ProblemMetaRepository repository, ObjectMapper objectMapper) {
        this.repository = repository;
        this.objectMapper = objectMapper;
    }

    @Override
    public void run(String... args) throws Exception {
        if (repository.count() == 0) {
            seedLegacyEntries();
        }
        loadIntoRegistry();
    }

    // One-time migration of the 20 entries that used to live hand-typed in
    // ProblemMetaRegistry.java's static block, so problems 1-20 keep working
    // exactly as before without anyone touching the database by hand.
    private void seedLegacyEntries() {
        List<ProblemMetaEntity> legacy = new ArrayList<>();

        legacy.add(entry(1L, "twoSum", "int[]",
                param("nums", "int[]"), param("target", "int")));
        legacy.add(entry(2L, "addTwoNumbers", "ListNode",
                param("l1", "ListNode"), param("l2", "ListNode")));
        legacy.add(entry(3L, "romanToInt", "int",
                param("s", "String")));
        legacy.add(entry(4L, "longestCommonPrefix", "String",
                param("strs", "String[]")));
        legacy.add(entry(5L, "isValid", "boolean",
                param("s", "String")));
        legacy.add(entry(6L, "mergeTwoLists", "ListNode",
                param("list1", "ListNode"), param("list2", "ListNode")));
        legacy.add(entry(7L, "searchInsert", "int",
                param("nums", "int[]"), param("target", "int")));
        legacy.add(entry(8L, "maxSubArray", "int",
                param("nums", "int[]")));
        legacy.add(entry(9L, "plusOne", "int[]",
                param("digits", "int[]")));
        legacy.add(entryWithOutput(10L, "merge", "void", "nums1",
                param("nums1", "int[]"), param("m", "int"),
                param("nums2", "int[]"), param("n", "int")));
        legacy.add(entry(11L, "inorderTraversal", "List<Integer>",
                param("root", "TreeNode")));
        legacy.add(entry(12L, "isSameTree", "boolean",
                param("p", "TreeNode"), param("q", "TreeNode")));
        legacy.add(entry(13L, "isSymmetric", "boolean",
                param("root", "TreeNode")));
        legacy.add(entry(14L, "maxDepth", "int",
                param("root", "TreeNode")));
        legacy.add(entry(15L, "maxProfit", "int",
                param("prices", "int[]")));
        legacy.add(entry(16L, "isPalindrome", "boolean",
                param("s", "String")));
        legacy.add(entry(17L, "hasCycle", "boolean",
                param("head", "ListNode")));
        legacy.add(entry(18L, "reverseList", "ListNode",
                param("head", "ListNode")));
        legacy.add(entry(19L, "invertTree", "TreeNode",
                param("root", "TreeNode")));
        legacy.add(entry(20L, "isAnagram", "boolean",
                param("s", "String"), param("t", "String")));

        repository.saveAll(legacy);
    }

    // Reads every row in problem_meta and populates the in-memory registry
    // that StarterCodeGenerator/JavaDriverGenerator actually read from.
    private void loadIntoRegistry() throws Exception {
        Map<Long, ProblemMeta> data = new HashMap<>();

        for (ProblemMetaEntity row : repository.findAll()) {
            List<Map<String, String>> rawParams = objectMapper.readValue(
                    row.getParamsJson(), new TypeReference<List<Map<String, String>>>() {}
            );

            List<ProblemMeta.Param> params = new ArrayList<>();
            for (Map<String, String> p : rawParams) {
                params.add(new ProblemMeta.Param(p.get("name"), p.get("type")));
            }

            data.put(row.getProblemId(), new ProblemMeta(
                    row.getMethodName(), row.getReturnType(), params, row.getOutputParam()));
        }

        ProblemMetaRegistry.load(data);
        System.out.println("✅ Loaded " + data.size() + " problem signatures into ProblemMetaRegistry");
    }

    private Map<String, String> param(String name, String type) {
        Map<String, String> p = new LinkedHashMap<>();
        p.put("name", name);
        p.put("type", type);
        return p;
    }

    private ProblemMetaEntity entry(Long problemId, String methodName, String returnType,
                                     @SuppressWarnings("unchecked") Map<String, String>... params) {
        return entryWithOutput(problemId, methodName, returnType, null, params);
    }

    private ProblemMetaEntity entryWithOutput(Long problemId, String methodName, String returnType,
                                               String outputParam,
                                               @SuppressWarnings("unchecked") Map<String, String>... params) {
        ProblemMetaEntity e = new ProblemMetaEntity();
        e.setProblemId(problemId);
        e.setMethodName(methodName);
        e.setReturnType(returnType);
        e.setOutputParam(outputParam);
        try {
            e.setParamsJson(objectMapper.writeValueAsString(Arrays.asList(params)));
        } catch (Exception ex) {
            e.setParamsJson("[]");
        }
        return e;
    }
}