package com.example.demo.dto;

import java.util.List;

public class SchoolClassResponseDto {

    private Long id;
    private String name;
    private List<Long> subjectIds;
    private List<Long> studentIds;

    public SchoolClassResponseDto() {
    }

    public SchoolClassResponseDto(Long id, String name, List<Long> subjectIds, List<Long> studentIds) {
        this.id = id;
        this.name = name;
        this.subjectIds = subjectIds;
        this.studentIds = studentIds;
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

    public List<Long> getStudentIds() {
        return studentIds;
    }

    public void setStudentIds(List<Long> studentIds) {
        this.studentIds = studentIds;
    }

    public List<Long> getSubjectIds() {
        return subjectIds;
    }

    public void setSubjectIds(List<Long> subjectIds) {
        this.subjectIds = subjectIds;
    }
}
