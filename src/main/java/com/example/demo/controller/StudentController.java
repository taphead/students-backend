package com.example.demo.controller;

import com.example.demo.dto.CreateStudentDto;
import com.example.demo.dto.StudentResponseDto;
import com.example.demo.dto.UpdateStudentDto;
import com.example.demo.service.StudentService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;


@RestController
@RequestMapping("/students")
@CrossOrigin(origins = "http://localhost:3000")
public class StudentController {
    private final StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @GetMapping
    public Page<StudentResponseDto> getAllStudents(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size,
                                                   @RequestParam(defaultValue = "id") String sortBy,
                                                   @RequestParam(defaultValue = "asc") String direction,
                                                   @RequestParam(required = false) String name) {

        return studentService.getAllStudents(page, size, sortBy, direction, name);
    }

    @GetMapping("/{id}")
    public StudentResponseDto getStudentById(@PathVariable Long id) {
        return studentService.getStudentById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public StudentResponseDto createStudent(@Valid @RequestBody CreateStudentDto dto) {
        return studentService.createStudent(dto);
    }

    @PutMapping("/{id}")
    public StudentResponseDto updateStudent(@PathVariable Long id, @Valid @RequestBody UpdateStudentDto dto) {
        return studentService.updateStudent(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }
}
