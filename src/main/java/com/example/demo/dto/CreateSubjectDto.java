package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;

public class CreateSubjectDto {

    @NotBlank(message = "Subject name is required")
    private String name;

    public CreateSubjectDto() {
    }

    public CreateSubjectDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}