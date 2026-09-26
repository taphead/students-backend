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

    public static Specification<Student> hasMinAge(Integer minAge) {

        return (root, query, cb) -> cb.greaterThanOrEqualTo(root.get("age"), minAge);
    }

    public static Specification<Student> hasMaxAge(Integer maxAge) {

        return (root, query, cb) -> cb.lessThanOrEqualTo(root.get("age"), maxAge);
    }

    public static Specification<Student> hasSchoolClassId(Long schoolClassId) {

        return (root, query, cb) -> cb.equal(root.get("schoolClass").get("id"), schoolClassId);
    }
}
