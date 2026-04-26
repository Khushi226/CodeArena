package com.codearena.backend.judge.array;

import com.fasterxml.jackson.databind.ObjectMapper;

import java.util.List;
import java.util.Map;

public class ArrayInputParser {

    private static final ObjectMapper objectMapper = new ObjectMapper();

    /**
     * Parses input_json into a generic Map
     */
    public static Map<String, Object> parseToMap(String inputJson) {
        try {
            return objectMapper.readValue(inputJson, Map.class);
        } catch (Exception e) {
            throw new RuntimeException(
                    "Failed to parse input_json into Map: " + inputJson,
                    e
            );
        }
    }

    /* -------------------- Typed Extractors -------------------- */

    public static int[] getIntArray(Map<String, Object> map, String key) {
        List<Integer> list = (List<Integer>) map.get(key);

        if (list == null) {
            throw new RuntimeException("Missing array key: " + key);
        }

        int[] arr = new int[list.size()];
        for (int i = 0; i < list.size(); i++) {
            arr[i] = list.get(i);
        }
        return arr;
    }

    public static int getInt(Map<String, Object> map, String key) {
        Object val = map.get(key);
        if (val == null) {
            throw new RuntimeException("Missing int key: " + key);
        }
        return ((Number) val).intValue();
    }

    public static long getLong(Map<String, Object> map, String key) {
        Object val = map.get(key);
        if (val == null) {
            throw new RuntimeException("Missing long key: " + key);
        }
        return ((Number) val).longValue();
    }

    public static String getString(Map<String, Object> map, String key) {
        Object val = map.get(key);
        if (val == null) {
            throw new RuntimeException("Missing string key: " + key);
        }
        return val.toString();
    }

    /* -------------------- Backward compatibility -------------------- */

    // Plus One convenience
    public static int[] parseDigits(String inputJson) {
        Map<String, Object> map = parseToMap(inputJson);
        return getIntArray(map, "digits");
    }
}
