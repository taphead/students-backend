package com.example.demo.service;

import com.example.demo.dto.CreateStudentDto;
import com.example.demo.dto.StudentResponseDto;
import com.example.demo.dto.UpdateStudentDto;
import com.example.demo.entity.SchoolClass;
import com.example.demo.entity.Student;
import com.example.demo.exception.ResourceNotFoundException;
import com.example.demo.repository.SchoolClassRepository;
import com.example.demo.repository.StudentRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Set;


@Service
public class StudentService {

    private final StudentRepository studentRepository;
    public final SchoolClassRepository schoolClassRepository;
    private static final Set<String> ALLOWED_SORT_FIELDS = Set.of(
            "id",
            "name",
            "email",
            "age"
    );
    private static final Set<String> ALLOWED_DIRECTION_FIELDS = Set.of(
            "asc",
            "desc"
    );

    public StudentService(StudentRepository studentRepository, SchoolClassRepository schoolClassRepository) {
        this.studentRepository = studentRepository;
        this.schoolClassRepository = schoolClassRepository;
    }

    public Page<StudentResponseDto> getAllStudents(
            int page,
            int size,
            String sortBy,
            String direction,
            String name
    ) {

        if (!ALLOWED_SORT_FIELDS.contains(sortBy.toLowerCase())) {
            throw new IllegalArgumentException("Invalid Sort Field: " + sortBy);
        }

        if (!ALLOWED_DIRECTION_FIELDS.contains(direction.toLowerCase())) {
            throw new IllegalArgumentException("Invalid Direction Field: " + direction);
        }

        Sort sort;

        if (direction.equalsIgnoreCase("desc")) {
            sort = Sort.by(sortBy).descending();
        } else {
            sort = Sort.by(sortBy).ascending();
        }

        Pageable pageable = PageRequest.of(page, size, sort);

        Page<Student> studentPage;

        if (name != null && !name.isEmpty()) {
            studentPage = studentRepository.findByNameContainingIgnoreCase(name, pageable);
        } else {
            studentPage = studentRepository.findAll(pageable);
        }

        return studentPage.map(this::mapToResponseDto);
    }

    public StudentResponseDto getStudentById(Long id) {

        Student student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found with id: " + id));

        return mapToResponseDto(student);
    }

    public StudentResponseDto createStudent(CreateStudentDto dto) {

        SchoolClass schoolClass = schoolClassRepository.findById(dto.getSchoolClassId()).orElseThrow(() -> new RuntimeException("School class not found with id: " + dto.getSchoolClassId()));


        Student student = new Student();

        student.setName(dto.getName());
        student.setEmail(dto.getEmail());
        student.setAge(dto.getAge());

        student.setSchoolClass(schoolClass);


        Student savedStudent = studentRepository.save(student);


        return mapToResponseDto(savedStudent);
    }

    public StudentResponseDto updateStudent(Long id, UpdateStudentDto dto) {

        Student student = studentRepository.findById(id).orElseThrow(() -> new RuntimeException("Student not found with id: " + id));

        if (dto.getName() != null) {

            student.setName(dto.getName());
        }

        if (dto.getEmail() != null) {

            student.setEmail(dto.getEmail());
        }

        if (dto.getAge() != null) {

            student.setAge(dto.getAge());
        }

        if (dto.getSchoolClassId() != null) {

            SchoolClass schoolClass = schoolClassRepository.findById(dto.getSchoolClassId()).orElseThrow(() -> new RuntimeException("School class not found with id: " + dto.getSchoolClassId()));

            student.setSchoolClass(schoolClass);
        }


        Student updatedStudent = studentRepository.save(student);


        return mapToResponseDto(updatedStudent);
    }

    public void deleteStudent(Long id) {
        Student student = studentRepository.findById(id).orElseThrow(() -> new ResourceNotFoundException("Student not found with id: " + id));
        studentRepository.delete(student);
    }

    private StudentResponseDto mapToResponseDto(Student student) {

        Long schoolClassId = null;


        if (student.getSchoolClass() != null) {

            schoolClassId = student.getSchoolClass().getId();
        }


        return new StudentResponseDto(

                student.getId(),

                student.getName(),

                student.getEmail(),

                student.getAge(),

                schoolClassId);
    }
}
