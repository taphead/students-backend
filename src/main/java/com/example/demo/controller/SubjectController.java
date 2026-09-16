package com.example.demo.controller;

import com.example.demo.dto.CreateSubjectDto;
import com.example.demo.dto.SubjectResponseDto;
import com.example.demo.dto.UpdateSubjectDto;
import com.example.demo.entity.Subject;
import com.example.demo.service.SubjectService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
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
    public Page<SubjectResponseDto> getAllSubjects(@RequestParam(defaultValue = "0") int page,
                                                   @RequestParam(defaultValue = "10") int size,
                                                   @RequestParam(defaultValue = "id") String sortBy,
                                                   @RequestParam(defaultValue = "asc") String direction) {

        return subjectService.getAllSubjects(page, size, sortBy, direction);
    }

    @GetMapping("/{id}")
    public SubjectResponseDto getSubjectById(@PathVariable Long id) {
        return subjectService.getSubjectById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SubjectResponseDto createSubject(
            @Valid @RequestBody CreateSubjectDto dto) {

        return subjectService.createSubject(dto);
    }

    @PutMapping("/{id}")
    public SubjectResponseDto updateSubject(
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