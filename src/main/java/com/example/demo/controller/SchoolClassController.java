package com.example.demo.controller;

import com.example.demo.dto.CreateSchoolClassDto;
import com.example.demo.dto.SchoolClassResponseDto;
import com.example.demo.dto.UpdateSchoolClassDto;
import com.example.demo.entity.SchoolClass;
import com.example.demo.service.SchoolClassService;
import jakarta.validation.Valid;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/classes")
@CrossOrigin(origins = "http://localhost:3000")
public class SchoolClassController {

    private final SchoolClassService schoolClassService;

    public SchoolClassController(SchoolClassService schoolClassService) {
        this.schoolClassService = schoolClassService;
    }

    @GetMapping
    public Page<SchoolClassResponseDto> getAllClasses(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String direction) {

        return schoolClassService.getAllClasses(page, size, sortBy, direction);
    }

    @GetMapping("/{id}")
    public SchoolClassResponseDto getClassById(@PathVariable Long id) {
        return schoolClassService.getClassById(id);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public SchoolClassResponseDto createClass(
            @Valid @RequestBody CreateSchoolClassDto dto) {

        return schoolClassService.createClass(dto);
    }

    @PutMapping("/{id}")
    public SchoolClassResponseDto updateClass(
            @PathVariable Long id,
            @Valid @RequestBody UpdateSchoolClassDto dto) {

        return schoolClassService.updateClass(id, dto);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteClass(@PathVariable Long id) {
        schoolClassService.deleteClass(id);
    }
}