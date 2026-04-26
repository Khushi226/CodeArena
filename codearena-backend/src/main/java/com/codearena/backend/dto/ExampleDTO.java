package com.codearena.backend.dto;

import com.fasterxml.jackson.annotation.JsonIgnoreProperties;

import lombok.Data;

// public class ExampleDTO {

//     private int example_num;
//     private String example_text;

//     public int getExample_num() { return example_num; }
//     public void setExample_num(int example_num) { this.example_num = example_num; }

//     public String getExample_text() { return example_text; }
//     public void setExample_text(String example_text) { this.example_text = example_text; }
// }

@Data
@JsonIgnoreProperties(ignoreUnknown = true) // Ignores "images" and other extra fields
public class ExampleDTO {
    private String example_num;
    private String example_text;
    // Jackson will now ignore the "images" field in your JSON
}