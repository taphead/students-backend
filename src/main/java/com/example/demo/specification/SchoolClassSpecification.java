package com.example.demo.specification;

import com.example.demo.entity.SchoolClass;
import com.example.demo.entity.Subject;
import org.springframework.data.jpa.domain.Specification;

public class SchoolClassSpecification {

    public static Specification<SchoolClass> hasName(String name) {

        return (root, query, cb) -> cb.like(cb.lower(root.get("name")), "%" + name.toLowerCase() + "%");
    }

    public static Specification<SchoolClass> hasId(Long id) {

        return (root, query, cb) -> cb.equal(root.get("id"), id);
    }
}
