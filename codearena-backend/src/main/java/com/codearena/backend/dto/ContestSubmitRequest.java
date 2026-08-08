package com.codearena.backend.dto;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class ContestSubmitRequest {
    private Long problemId;
    private String language;
    private String code;
}