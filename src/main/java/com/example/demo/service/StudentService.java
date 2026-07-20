package com.example.demo.service;

import com.example.demo.dto.CreateStudentDto;
import com.example.demo.dto.UpdateStudentDto;
import com.example.demo.entity.SchoolClass;
import com.example.demo.entity.Student;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.SchoolClassRepository;
import com.example.demo.repository.StudentRepository;
import org.springframework.stereotype.Service;

import java.util.List;


@Service
public class StudentService {
    private final StudentRepository studentRepository;
    public final SchoolClassRepository schoolClassRepository;

    // Constructor injection
    public StudentService(StudentRepository studentRepository, SchoolClassRepository schoolClassRepository) {
        this.studentRepository = studentRepository;
        this.schoolClassRepository = schoolClassRepository;
    }

    public List<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Student getStudentById(Long id) {
        return studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
    }

    public Student createStudent(CreateStudentDto dto) {

        SchoolClass schoolClass = schoolClassRepository.findById(dto.getSchoolClassId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Class not found with id: " + dto.getSchoolClassId()));

        Student student = new Student();
        student.setName(dto.getName());
        student.setEmail(dto.getEmail());
        student.setAge(dto.getAge());
        student.setSchoolClass(schoolClass);

        return studentRepository.save(student);
    }

    public Student updateStudent(Long id, UpdateStudentDto dto) {

        Student student = studentRepository.findById(id)
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Student not found with id: " + id));

        SchoolClass schoolClass = schoolClassRepository.findById(dto.getSchoolClassId())
                .orElseThrow(() ->
                        new ResourceNotFoundException(
                                "Class not found with id: " + dto.getSchoolClassId()));

        student.setName(dto.getName());
        student.setEmail(dto.getEmail());
        student.setAge(dto.getAge());
        student.setSchoolClass(schoolClass);

        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
        studentRepository.delete(student);
    }
}
