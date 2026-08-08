package com.codearena.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
import lombok.Data;

import java.util.List;

// This is the piece that used to only exist as a hand-written entry in
// ProblemMetaRegistry.java. Now it's just another part of the import JSON —
// see problems/*.json for the expected shape.
@Data
@JsonIgnoreProperties(ignoreUnknown = true)
public class MethodSignatureDTO {
    private String methodName;
    private String returnType;
    private List<MethodParamDTO> params;

    // Required only when returnType is "void" — names the parameter that
    // holds the answer after the call mutates it (e.g. "nums1" for "merge").
    private String outputParam;
}