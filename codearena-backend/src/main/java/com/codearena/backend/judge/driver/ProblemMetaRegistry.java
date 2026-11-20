

package com.codearena.backend.judge.driver;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

// This used to be a static block hand-editing 20 hardcoded entries at
// compile time — every new problem required editing this file and
// redeploying. It's now an in-memory cache populated at runtime:
//   - on startup, from every row in the problem_meta table (ProblemMetaLoader)
//   - incrementally, the moment a new problem is imported (ProblemImportService)
// get()/put() keep the exact same signatures StarterCodeGenerator and
// JavaDriverGenerator already call, so neither of those needed to change.
public class ProblemMetaRegistry {

    private static final Map<Long, ProblemMeta> REGISTRY = new ConcurrentHashMap<>();

    public static ProblemMeta get(Long problemId) {
        return REGISTRY.get(problemId);
    }

    public static void put(Long problemId, ProblemMeta meta) {
        REGISTRY.put(problemId, meta);
    }

    // Bulk-replace at startup. Doesn't clear() first — if this is ever called
    // more than once, entries already present but missing from `data` are left
    // alone rather than dropped, which is the safer default for a cache.
    public static void load(Map<Long, ProblemMeta> data) {
        REGISTRY.putAll(data);
    }

    public static int size() {
        return REGISTRY.size();
    }
}