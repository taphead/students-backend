package com.example.demo.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public class CreateStudentDto {

    @NotBlank(message = "Name is required")
    private String name;

    @Email(message = "Must be valid email")
    @NotBlank(message = "Email is required")
    private String email;

    @Min(value = 1, message = "Age must be greater than 0")
    private Integer age;

    @NotNull
    private Long schoolClassId;

    public CreateStudentDto() {
    }

    public CreateStudentDto(String name, String email, Integer age) {
        this.name = name;
        this.email = email;
        this.age = age;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Long getSchoolClassId() {
        return schoolClassId;
    }
}
