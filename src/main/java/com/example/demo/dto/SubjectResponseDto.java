package com.example.demo.dto;

import java.util.List;

public class SubjectResponseDto {
    private Long id;
    private String name;
    private List<Long> classIds;

    public SubjectResponseDto() {
    }

    public SubjectResponseDto(Long id, String name, List<Long> classIds) {
        this.id = id;
        this.name = name;
        this.classIds = classIds;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Long> getClassIds() {
        return classIds;
    }

    public void setClassIds(List<Long> classIds) {
        this.classIds = classIds;
    }
}
