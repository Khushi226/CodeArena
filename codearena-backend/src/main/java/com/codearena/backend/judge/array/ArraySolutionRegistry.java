package com.codearena.backend.judge.array;

import java.util.HashMap;
import java.util.Map;

public class ArraySolutionRegistry {

    private static final Map<String, ArraySolution> SOLUTIONS = new HashMap<>();

    static {
        SOLUTIONS.put("plus-one", new PlusOneSolution());
        // SOLUTIONS.put("sorted-squares", new SortedSquaresSolution());
        SOLUTIONS.put("search-insert-position", new SearchInsertPositionSolution());
        SOLUTIONS.put("maximum-subarray",new MaximumSubarraySolution());
        SOLUTIONS.put("merge-sorted-array", new MergeSortedArraySolution());
        SOLUTIONS.put("two-sum",new TwoSumSolution());
        SOLUTIONS.put("best-time-to-buy-and-sell-stock", new BestTimeToBuyAndSellStockSolution());
        // add more ARRAY problems here
    }

    public static ArraySolution getSolution(String problemSlug) {
        return SOLUTIONS.get(problemSlug);
    }
}
