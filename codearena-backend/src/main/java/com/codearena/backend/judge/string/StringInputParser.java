package com.codearena.backend.judge.string;

import java.util.ArrayList;
import java.util.List;

public class StringInputParser {

    // For single string parameter
    public static String parseSingle(String input) {
        input = input.trim();

        if (input.startsWith("\"") && input.endsWith("\"")) {
            return input.substring(1, input.length() - 1);
        }

        return input;
    }

    // For multiple string parameters:  "abc", "def"
    public static String[] parseMultiple(String input) {
        List<String> result = new ArrayList<>();

        int i = 0;
        while (i < input.length()) {
            while (i < input.length() && input.charAt(i) != '"') i++;
            int start = ++i;

            while (i < input.length() && input.charAt(i) != '"') i++;
            int end = i++;

            result.add(input.substring(start, end));
        }

        return result.toArray(new String[0]);
    }
}