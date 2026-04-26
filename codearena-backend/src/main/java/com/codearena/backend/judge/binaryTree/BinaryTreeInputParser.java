package com.codearena.backend.judge.binaryTree;

import java.util.*;

public class BinaryTreeInputParser {

    public static TreeNode parse(String input) {

        input = input.trim();

        if (input.equals("[]")) return null;

        // remove [ and ]
        input = input.substring(1, input.length() - 1);

        String[] parts = input.split(",");

        TreeNode root = new TreeNode(Integer.parseInt(parts[0].trim()));

        Queue<TreeNode> queue = new LinkedList<>();
        queue.add(root);

        int i = 1;

        while (!queue.isEmpty() && i < parts.length) {

            TreeNode current = queue.poll();

            // Left child
            if (!parts[i].trim().equals("null")) {
                current.left = new TreeNode(Integer.parseInt(parts[i].trim()));
                queue.add(current.left);
            }
            i++;

            if (i >= parts.length) break;

            // Right child
            if (!parts[i].trim().equals("null")) {
                current.right = new TreeNode(Integer.parseInt(parts[i].trim()));
                queue.add(current.right);
            }
            i++;
        }

        return root;
    }
}