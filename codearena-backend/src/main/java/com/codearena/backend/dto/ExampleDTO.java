package com.codearena.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;


@Data
@JsonIgnoreProperties(ignoreUnknown = true) // Ignores "images" and other extra fields
public class ExampleDTO {
    private String example_num;
    private String example_text;
    // Jackson will now ignore the "images" field in your JSON
}