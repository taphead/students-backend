package com.example.demo.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotEmpty;

import java.util.List;

public class UpdateSchoolClassDto {

    @NotBlank(message = "Class name is required")
    private String name;

    @NotEmpty(message = "A class must have at least one subject")
    private List<Long> subjectIds;

    public UpdateSchoolClassDto() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getSubjectIds() {
        return subjectIds;
    }

    public void setSubjectIds(List<Long> subjectIds) {
        this.subjectIds = subjectIds;
    }
}