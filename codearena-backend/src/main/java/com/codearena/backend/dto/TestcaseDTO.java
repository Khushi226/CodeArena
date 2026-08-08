package com.codearena.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.Map;

@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class TestcaseDTO {
    // Keys must exactly match methodSignature.params[].name — JavaDriverGenerator
    // finds each argument by searching for "<paramName>": in this object.
    private Map<String, Object> input;

    // The exact stdout the driver will print for a correct solution:
    //   int[]/String[] -> Arrays.toString format, e.g. "[1, 2, 4]"
    //   ListNode       -> List.toString format, e.g. "[1, 2, 3]"
    //   TreeNode       -> level-order, e.g. "[1,2,3]" (no spaces)
    //   int/boolean/String -> the bare value, e.g. "true", "5", "hello"
    private String output;

    // "PUBLIC" (shown to the user in the problem statement) or "PRIVATE"
    // (hidden, used only for judging). Defaults to PRIVATE if omitted.
    private String visibility;
}