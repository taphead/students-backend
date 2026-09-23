package com.example.demo.repository;

import com.example.demo.entity.Student;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.stereotype.Repository;


@Repository
public interface StudentRepository extends JpaRepository<Student, Long>, JpaSpecificationExecutor<Student> {

    Page<Student> findByNameContainingIgnoreCase(String name, Pageable pageable);

    // age filter is optional, when user doesn't provide age we want age = null
    // Integer instead of int to allow null values as int cannot be null
    Page<Student> findByAge(Integer age, Pageable pageable);
}
