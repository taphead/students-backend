package com.example.demo.entity;

import jakarta.persistence.*;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "subjects")
public class Subject {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String name;

    @ManyToMany(mappedBy = "subjects")
    private List<SchoolClass> classes = new ArrayList<>();

    public Subject() {}

    public Long getId() {
        return id;
    }

    public String getName() {
        return name;
    }

    public List<SchoolClass> getClasses() {
        return classes;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setClasses(List<SchoolClass> classes) {
        this.classes = classes;
    }
}
