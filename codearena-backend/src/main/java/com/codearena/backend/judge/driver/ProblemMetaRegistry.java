// package com.codearena.backend.judge.driver;

// import java.util.*;

// public class ProblemMetaRegistry {

//     private static final Map<Long, ProblemMeta> REGISTRY = new HashMap<>();

//     static {
//         // problem_id = 9 → Plus One
//         REGISTRY.put(9L, new ProblemMeta(
//                 "plusOne",
//                 "int[]",
//                 List.of(
//                         new ProblemMeta.Param("digits", "int[]")
//                 )
//         ));

//         // problem_id = 7 → Search Insert Position
//         REGISTRY.put(7L, new ProblemMeta(
//                 "searchInsert",
//                 "int",
//                 List.of(
//                         new ProblemMeta.Param("nums", "int[]"),
//                         new ProblemMeta.Param("target", "int")
//                 )
//         ));
//     }

//     public static ProblemMeta get(Long problemId) {
//         return REGISTRY.get(problemId);
//     }
// }



package com.codearena.backend.judge.driver;

import java.util.*;

public class ProblemMetaRegistry {

    private static final Map<Long, ProblemMeta> REGISTRY = new HashMap<>();

    static {

        // ============================================================
        // 1) two-sum
        // Signature: int[] twoSum(int[] nums, int target)
        // ============================================================
        REGISTRY.put(1L, new ProblemMeta(
                "twoSum",
                "int[]",
                List.of(
                        new ProblemMeta.Param("nums", "int[]"),
                        new ProblemMeta.Param("target", "int")
                )
        ));

        // ============================================================
        // 2) add-two-numbers
        // Signature: ListNode addTwoNumbers(ListNode l1, ListNode l2)
        // ============================================================
        REGISTRY.put(2L, new ProblemMeta(
                "addTwoNumbers",
                "ListNode",
                List.of(
                        new ProblemMeta.Param("l1", "ListNode"),
                        new ProblemMeta.Param("l2", "ListNode")
                )
        ));

        // ============================================================
        // 3) roman-to-integer
        // Signature: int romanToInt(String s)
        // ============================================================
        REGISTRY.put(3L, new ProblemMeta(
                "romanToInt",
                "int",
                List.of(
                        new ProblemMeta.Param("s", "String")
                )
        ));

        // ============================================================
        // 4) longest-common-prefix
        // Signature: String longestCommonPrefix(String[] strs)
        // ============================================================
        REGISTRY.put(4L, new ProblemMeta(
                "longestCommonPrefix",
                "String",
                List.of(
                        new ProblemMeta.Param("strs", "String[]")
                )
        ));

        // ============================================================
        // 5) valid-parentheses
        // Signature: boolean isValid(String s)
        // ============================================================
        REGISTRY.put(5L, new ProblemMeta(
                "isValid",
                "boolean",
                List.of(
                        new ProblemMeta.Param("s", "String")
                )
        ));

        // ============================================================
        // 6) merge-two-sorted-lists
        // Signature: ListNode mergeTwoLists(ListNode list1, ListNode list2)
        // ============================================================
        REGISTRY.put(6L, new ProblemMeta(
                "mergeTwoLists",
                "ListNode",
                List.of(
                        new ProblemMeta.Param("list1", "ListNode"),
                        new ProblemMeta.Param("list2", "ListNode")
                )
        ));

        // ============================================================
        // 7) search-insert-position
        // Signature: int searchInsert(int[] nums, int target)
        // ============================================================
        REGISTRY.put(7L, new ProblemMeta(
                "searchInsert",
                "int",
                List.of(
                        new ProblemMeta.Param("nums", "int[]"),
                        new ProblemMeta.Param("target", "int")
                )
        ));

        // ============================================================
        // 8) maximum-subarray
        // Signature: int maxSubArray(int[] nums)
        // ============================================================
        REGISTRY.put(8L, new ProblemMeta(
                "maxSubArray",
                "int",
                List.of(
                        new ProblemMeta.Param("nums", "int[]")
                )
        ));

        // ============================================================
        // 9) plus-one
        // Signature: int[] plusOne(int[] digits)
        // ============================================================
        REGISTRY.put(9L, new ProblemMeta(
                "plusOne",
                "int[]",
                List.of(
                        new ProblemMeta.Param("digits", "int[]")
                )
        ));

        // ============================================================
        // 10) merge-sorted-array
        // Signature: void merge(int[] nums1, int m, int[] nums2, int n)
        // ============================================================
        REGISTRY.put(10L, new ProblemMeta(
                "merge",
                "void",
                List.of(
                        new ProblemMeta.Param("nums1", "int[]"),
                        new ProblemMeta.Param("m", "int"),
                        new ProblemMeta.Param("nums2", "int[]"),
                        new ProblemMeta.Param("n", "int")
                )
        ));

        // ============================================================
        // 11) binary-tree-inorder-traversal
        // Signature: List<Integer> inorderTraversal(TreeNode root)
        // ============================================================
        REGISTRY.put(11L, new ProblemMeta(
                "inorderTraversal",
                "List<Integer>",
                List.of(
                        new ProblemMeta.Param("root", "TreeNode")
                )
        ));

        // ============================================================
        // 12) same-tree
        // Signature: boolean isSameTree(TreeNode p, TreeNode q)
        // ============================================================
        REGISTRY.put(12L, new ProblemMeta(
                "isSameTree",
                "boolean",
                List.of(
                        new ProblemMeta.Param("p", "TreeNode"),
                        new ProblemMeta.Param("q", "TreeNode")
                )
        ));

        // ============================================================
        // 13) symmetric-tree
        // Signature: boolean isSymmetric(TreeNode root)
        // ============================================================
        REGISTRY.put(13L, new ProblemMeta(
                "isSymmetric",
                "boolean",
                List.of(
                        new ProblemMeta.Param("root", "TreeNode")
                )
        ));

        // ============================================================
        // 14) maximum-depth-of-binary-tree
        // Signature: int maxDepth(TreeNode root)
        // ============================================================
        REGISTRY.put(14L, new ProblemMeta(
                "maxDepth",
                "int",
                List.of(
                        new ProblemMeta.Param("root", "TreeNode")
                )
        ));

        // ============================================================
        // 15) best-time-to-buy-and-sell-stock
        // Signature: int maxProfit(int[] prices)
        // ============================================================
        REGISTRY.put(15L, new ProblemMeta(
                "maxProfit",
                "int",
                List.of(
                        new ProblemMeta.Param("prices", "int[]")
                )
        ));

        // ============================================================
        // 16) valid-palindrome
        // Signature: boolean isPalindrome(String s)
        // ============================================================
        REGISTRY.put(16L, new ProblemMeta(
                "isPalindrome",
                "boolean",
                List.of(
                        new ProblemMeta.Param("s", "String")
                )
        ));

        // ============================================================
        // 17) linked-list-cycle
        // Signature: boolean hasCycle(ListNode head)
        // ============================================================
        REGISTRY.put(17L, new ProblemMeta(
        "hasCycle",
        "boolean",
        List.of(
                new ProblemMeta.Param("head", "ListNode")
                // new ProblemMeta.Param("pos", "int")
        )
        ));


        // ============================================================
        // 18) reverse-linked-list
        // Signature: ListNode reverseList(ListNode head)
        // ============================================================
        REGISTRY.put(18L, new ProblemMeta(
                "reverseList",
                "ListNode",
                List.of(
                        new ProblemMeta.Param("head", "ListNode")
                )
        ));

        // ============================================================
        // 19) invert-binary-tree
        // Signature: TreeNode invertTree(TreeNode root)
        // ============================================================
        REGISTRY.put(19L, new ProblemMeta(
                "invertTree",
                "TreeNode",
                List.of(
                        new ProblemMeta.Param("root", "TreeNode")
                )
        ));

        // ============================================================
        // 20) valid-anagram
        // Signature: boolean isAnagram(String s, String t)
        // ============================================================
        REGISTRY.put(20L, new ProblemMeta(
                "isAnagram",
                "boolean",
                List.of(
                        new ProblemMeta.Param("s", "String"),
                        new ProblemMeta.Param("t", "String")
                )
        ));
    }

    public static ProblemMeta get(Long problemId) {
        return REGISTRY.get(problemId);
    }
}
