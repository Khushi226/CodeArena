package com.codearena.backend.judge.array;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;

public class ArrayOutputParser {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Parse output_json into generic Object
     */
    public static Object parseRaw(String outputJson) {
        try {
            return objectMapper.readValue(outputJson, Object.class);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to parse output_json: " + outputJson,
                    e
            );
        }
    }

    /* -------------------- Typed Extractors -------------------- */

    public static int[] asIntArray(Object raw) {
        if (!(raw instanceof List<?> list)) {
            throw new RuntimeException("Expected JSON array but got: " + raw);
        }

        int[] result = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            result[i] = ((Number) list.get(i)).intValue();
        }
        return result;
    }

    public static boolean asBoolean(Object raw) {
        if (!(raw instanceof Boolean)) {
            throw new RuntimeException("Expected boolean but got: " + raw);
        }
        return (Boolean) raw;
    }

    public static int asInt(Object raw) {
        if (!(raw instanceof Number)) {
            throw new RuntimeException("Expected number but got: " + raw);
        }
        return ((Number) raw).intValue();
    }

    public static long asLong(Object raw) {
        if (!(raw instanceof Number)) {
            throw new RuntimeException("Expected number but got: " + raw);
        }
        return ((Number) raw).longValue();
    }

    public static String asString(Object raw) {
        return raw.toString();
    }

    /* -------------------- Backward compatibility -------------------- */

    // For current ARRAY problems
    public static int[] parseExpectedArray(String outputJson) {
        Object raw = parseRaw(outputJson);
        return asIntArray(raw);
    }
}
