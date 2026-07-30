package com.example.demo.controller;

import com.example.demo.dto.CreateSubjectDto;
import com.example.demo.dto.UpdateSubjectDto;
import com.example.demo.entity.Subject;
import com.example.demo.service.SubjectService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/subjects")
@CrossOrigin(origins = "http://localhost:3000")
public class SubjectController {

    private final SubjectService subjectService;

    public SubjectController(SubjectService subjectService) {
        this.subjectService = subjectService;
    }

    @GetMapping
    public List<Subject> getAllSubjects() {
        return subjectService.getAllSubjects();
    }

    @GetMapping("/{id}")
    public Subject getSubjectById(@PathVariable Long id) {
        return subjectService.getSubjectById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Subject createSubject(
            @Valid @RequestBody CreateSubjectDto dto) {

        return subjectService.createSubject(dto);
    }

    @PutMapping("/{id}")
    public Subject updateSubject(
            @PathVariable Long id,
            @Valid @RequestBody UpdateSubjectDto dto) {

        return subjectService.updateSubject(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteSubject(@PathVariable Long id) {
        subjectService.deleteSubject(id);
    }
}