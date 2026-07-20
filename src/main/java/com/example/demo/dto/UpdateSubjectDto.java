package com.example.demo.dto;


import jakarta.validation.constraints.NotBlank;

public class UpdateSubjectDto {

    @NotBlank(message = "Subject name is required")
    private String name;

    public UpdateSubjectDto() {
    }

    public UpdateSubjectDto(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
