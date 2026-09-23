package com.example.demo.specification;

import com.example.demo.entity.Student;
import org.springframework.data.jpa.domain.Specification;

public class StudentSpecification {

    public static Specification<Student> hasName(String name) {

        return (root, query, cb) -> cb.like(
                cb.lower(root.get("name")),
                "%" + name.toLowerCase() + "%"
        );
    }

    public static Specification<Student> hasAge(int age) {

        return (root, query, cb) -> cb.equal(root.get("age"), age);
    }
}
