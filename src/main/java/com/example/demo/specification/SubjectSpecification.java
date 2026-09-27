package com.example.demo.specification;

import com.example.demo.entity.Subject;
import org.springframework.data.jpa.domain.Specification;

public class SubjectSpecification {

    public static Specification<Subject> hasName(String name) {

        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }
}
